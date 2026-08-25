package com.projeto.agendamentos.dtos.sala;

public record SalaResponse(
    Long id,
    String nome,
    String localizacao,
    String responsavel,
    Integer capacidade
) {}

