package com.projeto.agendamentos.exceptions.security;

public class CredenciasInvalidasException extends SecurityException {
    public CredenciasInvalidasException(String message) {
        super(message);
    }
}
