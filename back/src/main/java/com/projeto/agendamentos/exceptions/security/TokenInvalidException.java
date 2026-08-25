package com.projeto.agendamentos.exceptions.security;

public class TokenInvalidException extends SecurityException {
    public TokenInvalidException(String message) {
        super(message);
    }
}
