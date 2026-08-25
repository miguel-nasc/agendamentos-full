package com.projeto.agendamentos.dtos.sala;

import org.springframework.hateoas.RepresentationModel;

public record SalaRequest (
    String nome,
    String localizacao,
    String responsavel,
    Integer capacidade
) {}
