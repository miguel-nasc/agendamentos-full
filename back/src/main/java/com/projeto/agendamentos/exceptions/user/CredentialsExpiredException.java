package com.projeto.agendamentos.exceptions.user;

public class CredentialsExpiredException extends UserException {
    public CredentialsExpiredException(String message) {
        super(message);
    }
}
