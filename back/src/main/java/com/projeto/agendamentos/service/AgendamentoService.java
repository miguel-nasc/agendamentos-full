package com.projeto.agendamentos.service;

import com.projeto.agendamentos.dtos.agendamento.AgendamentoResponse;
import com.projeto.agendamentos.exceptions.agendamentos.AgendamentoNaoEncontradoException;
import com.projeto.agendamentos.exceptions.agendamentos.DataInvalidaException;
import com.projeto.agendamentos.exceptions.agendamentos.HoraInvalidaException;
import com.projeto.agendamentos.exceptions.agendamentos.ReservadoPorIsBlankException;
import com.projeto.agendamentos.exceptions.agendamentos.TituloIsBlankException;
import com.projeto.agendamentos.exceptions.sala.SalaInvalidaException;
import com.projeto.agendamentos.exceptions.sala.SalaNaoEncontradaException;
import com.projeto.agendamentos.exceptions.sala.SalaOcupadaException;
import com.projeto.agendamentos.mapper.ObjectMapperUtil;
import com.projeto.agendamentos.model.Agendamento;
import com.projeto.agendamentos.model.Sala;
import com.projeto.agendamentos.repository.AgendamentoRepository;
import com.projeto.agendamentos.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    private final AgendamentoRepository repository;
    private final SalaRepository salaRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    private static final DateTimeFormatter HORA_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm");

    public AgendamentoService(AgendamentoRepository repository, SalaRepository salaRepository) {
        this.repository = repository;
        this.salaRepository = salaRepository;
    }

    public void salvarAgendamento(String titulo, String reservado_por, Long salaId, String data,
                                  String hora_inicio, String hora_fim) {

        // 1. Validações de formato e regra de negócio local
        verificarDadosString(titulo, reservado_por);
        verificarId(salaId);
        Horario horarios = converterDadosHoras(hora_inicio, hora_fim);
        LocalDate dataValida = formatarData(data);

        // 2. Validações e buscas de banco de dados
        var sala = salaRepository.findById(salaId)
                .orElseThrow(() -> new SalaNaoEncontradaException("Sala não encontrada."));

        verificarConflito(salaId, dataValida, horarios.horaInicio(), horarios.horaFim());

        Agendamento agendamento = new Agendamento();
        atualizarAgendamento(titulo, reservado_por, agendamento, sala, horarios, dataValida);
        repository.save(agendamento);
    }

    public AgendamentoResponse buscarAgendamentoPorId(Long id) {
        verificarId(id);
        var agendamento = repository.findById(id)
                .orElseThrow(() -> new AgendamentoNaoEncontradoException("Agendamento não encontrado."));
        return toResponse(agendamento);
    }

    private AgendamentoResponse toResponse(Agendamento agendamento) {
        String agendamentoId = agendamento.getId().toString();
        String agendamentoTitulo = agendamento.getTitulo();
        String agendamentoReservadoPor = agendamento.getReservadoPor();
        String agendamentoData = agendamento.getData().format(DATE_FORMATTER);
        String agendamentoHora = agendamento.getHoraInicio().format(HORA_FORMATTER);
        String agendamentoFim = agendamento.getHoraFim().format(HORA_FORMATTER);
        Long agendamentoSalaId = agendamento.getSala().getId();

        return new AgendamentoResponse(
          agendamentoId, agendamentoTitulo, agendamentoReservadoPor, agendamentoData, agendamentoHora,
          agendamentoFim, agendamentoSalaId);
    }

    public void deletarAgendamento(Long id) {
        verificarId(id);
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new AgendamentoNaoEncontradoException("Agendamento não encontrado."));
        repository.delete(agendamento);
    }

    public Agendamento atualizarAgendamento(Long id, String titulo, String reservado_por,
                                            Long salaId, String data,
                                            String hora_inicio, String hora_fim) {
        verificarId(id);
        verificarDadosString(titulo, reservado_por);
        verificarId(salaId);

        Horario horarios = converterDadosHoras(hora_inicio, hora_fim);
        LocalDate dataValida = formatarData(data);

        Agendamento agendamentoExistente = repository.findById(id)
                .orElseThrow(() -> new AgendamentoNaoEncontradoException("Agendamento não encontrado."));

        var sala = salaRepository.findById(salaId)
                .orElseThrow(() -> new SalaNaoEncontradaException("Sala não encontrada."));

        verificarConflito(salaId, dataValida, horarios.horaInicio(), horarios.horaFim());

        atualizarAgendamento(titulo, reservado_por, agendamentoExistente, sala, horarios, dataValida);
        return repository.save(agendamentoExistente);
    }

    public List<AgendamentoResponse> listarTodosAgendamentos() {
        if (existsAgendamentos()) {
            return repository.findAll().stream()
                    .map(agendamento -> ObjectMapperUtil.mapTo(agendamento, AgendamentoResponse.class))
                    .collect(Collectors.toList());
        } else {
            throw new AgendamentoNaoEncontradoException("Nenhum agendamento encontrado.");
        }
    }

    // Métodos privados auxiliares

    private boolean existsAgendamentos() {
        return repository.count() > 0;
    }

    private Agendamento atualizarAgendamento(String titulo, String reservado_por, Agendamento agendamentoExistente, Sala sala,
                                             Horario horarios, LocalDate dataValida) {
        agendamentoExistente.setTitulo(titulo);
        agendamentoExistente.setReservado_por(reservado_por);
        agendamentoExistente.setSala(sala);
        agendamentoExistente.setData(dataValida);
        agendamentoExistente.setHoraInicio(horarios.horaInicio());
        agendamentoExistente.setHoraFim(horarios.horaFim());
        return agendamentoExistente;
    }

    private void verificarConflito(Long salaId, LocalDate data, LocalTime horaInicio, LocalTime horaFim) {
        if (repository.existeConflito(salaId, data, horaInicio, horaFim)) {
            throw new SalaOcupadaException("A sala já está reservada nesse período.");
        }
    }

    private void verificarId(Long id) {
        if (id == null || id <= 0) {
            throw new SalaInvalidaException("O ID da sala deve ser um valor positivo. Valores negativos não são aceitos!");
        }
    }

    private void verificarDadosString(String titulo, String reservadoPor) {
        if (titulo == null || titulo.isBlank()) {
            throw new TituloIsBlankException("O título é obrigatório.");
        }
        if (reservadoPor == null || reservadoPor.isBlank()) {
            throw new ReservadoPorIsBlankException("O campo 'reservado_por' é obrigatório.");
        }
    }

    private Horario converterDadosHoras(String horaInicio, String horaFim) {
        verificarHoraEntradaSaida(horaInicio, horaFim);

        try {
            LocalTime inicio = LocalTime.parse(horaInicio, HORA_FORMATTER);
            LocalTime fim = LocalTime.parse(horaFim, HORA_FORMATTER);

            if (!inicio.isBefore(fim)) {
                throw new HoraInvalidaException("A hora de início deve ser menor que a hora de fim.");
            }

            return new Horario(inicio, fim);

        } catch (DateTimeParseException e) {
            throw new HoraInvalidaException("As horas devem estar em um formato válido (HH:mm).");
        }
    }

    private static LocalDate formatarData(String data) {
        verificarData(data);

        try {
            LocalDate dataConvertida = LocalDate.parse(data, DATE_FORMATTER);

            int anoAtual = LocalDate.now().getYear();
            if (dataConvertida.getYear() < anoAtual) {
                throw new DataInvalidaException("A data não pode ser de um ano anterior ao ano atual.");
            }

            return dataConvertida;
        } catch (DateTimeParseException e) {
            throw new DataInvalidaException("A data informada é inválida. Use o formato dd/MM/yyyy.");
        }
    }

    private static void verificarData(String data) {
        if (data == null || data.isBlank()) {
            throw new IllegalArgumentException("A data é obrigatória.");
        }
    }

    private static void verificarHoraEntradaSaida(String horaInicio, String horaFim) {
        if (horaInicio == null || horaFim == null) {
            throw new IllegalArgumentException("A hora de início e a hora de fim são obrigatórias.");
        }
        if (horaInicio.isBlank() || horaFim.isBlank()) {
            throw new IllegalArgumentException("A hora de início e a hora de fim não podem estar vazias.");
        }
    }
}