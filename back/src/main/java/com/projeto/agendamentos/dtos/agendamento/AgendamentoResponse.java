package com.projeto.agendamentos.dtos.agendamento;

public record AgendamentoResponse(
    String id,
    String titulo,
    String reservado_por,
    String data,
    String horaInicio,
    String horaFim,
    Long salaId
) {}