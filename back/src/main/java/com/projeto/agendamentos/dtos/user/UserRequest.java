package com.projeto.agendamentos.dtos.user;

public record UserRequest(
    String username,
    String fullname,
    String email,
    String password
) {}