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
import com.projeto.agendamentos.model.Agendamento;
import com.projeto.agendamentos.model.Sala;
import com.projeto.agendamentos.repository.AgendamentoRepository;
import com.projeto.agendamentos.repository.SalaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository repository;

    @Mock
    private SalaRepository salaRepository;

    @InjectMocks
    private AgendamentoService agendamentoService;

    private String dataValida;

    @BeforeEach
    void setUp() {
        // Garantir que a data seja válida independentemente do ano atual de execução
        dataValida = "15/10/" + LocalDate.now().getYear();
    }

    @Nested
    @DisplayName("Testes de Criação e Validação de Agendamento")
    class SalvarAgendamentoTestes {

        @Test
        @DisplayName("Deve salvar agendamento com sucesso quando os dados forem válidos")
        void deveSalvarAgendamentoComSucesso() {
            Long salaId = 1L;
            Sala sala = new Sala();

            when(salaRepository.findById(salaId)).thenReturn(Optional.of(sala));
            when(repository.existeConflito(eq(salaId), any(LocalDate.class), any(LocalTime.class), any(LocalTime.class)))
                    .thenReturn(false);

            agendamentoService.salvarAgendamento("Reunião de Alinhamento", "João", salaId, dataValida, "10:00", "11:00");

            ArgumentCaptor<Agendamento> captor = ArgumentCaptor.forClass(Agendamento.class);
            verify(repository, times(1)).save(captor.capture());

            Agendamento salvo = captor.getValue();
            assertEquals("Reunião de Alinhamento", salvo.getTitulo());
            assertEquals("João", salvo.getReservadoPor());
            assertEquals(sala, salvo.getSala());
            assertEquals(LocalTime.of(10, 0), salvo.getHoraInicio());
            assertEquals(LocalTime.of(11, 0), salvo.getHoraFim());
        }

        @Test
        @DisplayName("Deve lançar exceção quando o título ou reservado_por forem inválidos")
        void deveLancarExcecaoParaDadosStringInvalidos() {
            assertThrows(TituloIsBlankException.class, () ->
                    agendamentoService.salvarAgendamento("", "João", 1L, dataValida, "10:00", "11:00"));

            assertThrows(ReservadoPorIsBlankException.class, () ->
                    agendamentoService.salvarAgendamento("Reunião", "   ", 1L, dataValida, "10:00", "11:00"));

            verify(repository, never()).save(any());
        }

        @Test
        @DisplayName("Deve lançar exceção quando o ID da sala for inválido ou não encontrada")
        void deveLancarExcecaoParaSalaInvalidaOuInexistente() {
            assertThrows(SalaInvalidaException.class, () ->
                    agendamentoService.salvarAgendamento("Reunião", "João", -1L, dataValida, "10:00", "11:00"));

            when(salaRepository.findById(99L)).thenReturn(Optional.empty());
            assertThrows(SalaNaoEncontradaException.class, () ->
                    agendamentoService.salvarAgendamento("Reunião", "João", 99L, dataValida, "10:00", "11:00"));

            verify(repository, never()).save(any());
        }

        @Test
        @DisplayName("Deve lançar exceção quando a data for inválida ou de ano anterior")
        void deveLancarExcecaoParaDataInvalida() {
            assertThrows(IllegalArgumentException.class, () ->
                    agendamentoService.salvarAgendamento("Reunião", "João", 1L, "", "10:00", "11:00"));

            assertThrows(DataInvalidaException.class, () ->
                    agendamentoService.salvarAgendamento("Reunião", "João", 1L, "31/02/2026", "10:00", "11:00"));

            assertThrows(DataInvalidaException.class, () ->
                    agendamentoService.salvarAgendamento("Reunião", "João", 1L, "10/10/2000", "10:00", "11:00"));
        }

        @Test
        @DisplayName("Deve lançar exceção para horário de início maior/igual ao fim ou formato incorreto")
        void deveLancarExcecaoParaHorariosInvalidos() {
            assertThrows(IllegalArgumentException.class, () ->
                    agendamentoService.salvarAgendamento("Reunião", "João", 1L, dataValida, "", "11:00"));

            assertThrows(HoraInvalidaException.class, () ->
                    agendamentoService.salvarAgendamento("Reunião", "João", 1L, dataValida, "12:00", "11:00"));

            assertThrows(HoraInvalidaException.class, () ->
                    agendamentoService.salvarAgendamento("Reunião", "João", 1L, dataValida, "25:00", "11:00"));
        }

        @Test
        @DisplayName("Deve lançar exceção quando houver conflito de horário na sala")
        void deveLancarExcecaoQuandoHouverConflito() {
            Long salaId = 1L;
            when(salaRepository.findById(salaId)).thenReturn(Optional.of(new Sala()));
            when(repository.existeConflito(eq(salaId), any(LocalDate.class), any(LocalTime.class), any(LocalTime.class)))
                    .thenReturn(true);

            assertThrows(SalaOcupadaException.class, () ->
                    agendamentoService.salvarAgendamento("Reunião", "João", salaId, dataValida, "10:00", "11:00"));

            verify(repository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Testes de Busca por Agendamento")
    class BuscarAgendamentoTestes {

        @Test
        @DisplayName("Deve retornar agendamento ao buscar por ID existente")
        void deveRetornarAgendamentoPorId() {
            Agendamento agendamento = new Agendamento();
            agendamento.setTitulo("Reunião");

            when(repository.findById(1L)).thenReturn(Optional.of(agendamento));

            AgendamentoResponse resultado = agendamentoService.buscarAgendamentoPorId(1L);

            assertNotNull(resultado);
            assertEquals("Reunião", resultado.titulo());
            verify(repository, times(1)).findById(1L);
        }

        @Test
        @DisplayName("Deve lançar exceção ao buscar por ID inexistente ou inválido")
        void deveLancarExcecaoParaIdInexistenteOuInvalido() {
            assertThrows(SalaInvalidaException.class, () -> agendamentoService.buscarAgendamentoPorId(0L));

            when(repository.findById(99L)).thenReturn(Optional.empty());
            assertThrows(AgendamentoNaoEncontradoException.class, () -> agendamentoService.buscarAgendamentoPorId(99L));
        }
    }

    @Nested
    @DisplayName("Testes de Exclusão de Agendamento")
    class DeletarAgendamentoTestes {

        @Test
        @DisplayName("Deve deletar agendamento com sucesso")
        void deveDeletarAgendamento() {
            Agendamento agendamento = new Agendamento();
            when(repository.findById(1L)).thenReturn(Optional.of(agendamento));

            agendamentoService.deletarAgendamento(1L);

            verify(repository, times(1)).delete(agendamento);
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar deletar agendamento inexistente")
        void deveLancarExcecaoAoDeletarInexistente() {
            when(repository.findById(99L)).thenReturn(Optional.empty());

            assertThrows(AgendamentoNaoEncontradoException.class, () -> agendamentoService.deletarAgendamento(99L));
            verify(repository, never()).delete(any());
        }
    }

    @Nested
    @DisplayName("Testes de Atualização de Agendamento")
    class AtualizarAgendamentoTestes {

        @Test
        @DisplayName("Deve atualizar agendamento com sucesso")
        void deveAtualizarAgendamentoComSucesso() {
            Long agendamentoId = 1L;
            Long salaId = 2L;

            Agendamento agendamentoExistente = new Agendamento();
            Sala salaNova = new Sala();

            when(repository.findById(agendamentoId)).thenReturn(Optional.of(agendamentoExistente));
            when(salaRepository.findById(salaId)).thenReturn(Optional.of(salaNova));
            when(repository.existeConflito(eq(salaId), any(LocalDate.class), any(LocalTime.class), any(LocalTime.class)))
                    .thenReturn(false);

            when(repository.save(any(Agendamento.class))).thenAnswer(invocation -> invocation.getArgument(0));

            Agendamento atualizado = agendamentoService.atualizarAgendamento(
                    agendamentoId, "Novo Título", "Maria", salaId, dataValida, "14:00", "15:00"
            );

            assertNotNull(atualizado);
            assertEquals("Novo Título", atualizado.getTitulo());
            assertEquals("Maria", atualizado.getReservadoPor());
            assertEquals(salaNova, atualizado.getSala());
            verify(repository, times(1)).save(agendamentoExistente);
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar atualizar agendamento que não existe")
        void deveLancarExcecaoAoAtualizarInexistente() {
            when(repository.findById(99L)).thenReturn(Optional.empty());

            assertThrows(AgendamentoNaoEncontradoException.class, () ->
                    agendamentoService.atualizarAgendamento(99L, "Novo Título", "Maria", 1L, dataValida, "14:00", "15:00"));

            verify(repository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Testes de Listagem de Agendamentos")
    class ListarAgendamentosTestes {

        @Test
        @DisplayName("Deve listar todos os agendamentos quando existirem registros")
        void deveListarAgendamentos() {
            when(repository.count()).thenReturn(2L);
            when(repository.findAll()).thenReturn(List.of(new Agendamento(), new Agendamento()));

            Iterable<AgendamentoResponse> resultado = agendamentoService.listarTodosAgendamentos();

            assertNotNull(resultado);
            verify(repository, times(1)).findAll();
        }

        @Test
        @DisplayName("Deve lançar exceção quando não houver agendamentos cadastrados")
        void deveLancarExcecaoQuandoListaVazia() {
            when(repository.count()).thenReturn(0L);

            assertThrows(AgendamentoNaoEncontradoException.class, () -> agendamentoService.listarTodosAgendamentos());
            verify(repository, never()).findAll();
        }
    }
}