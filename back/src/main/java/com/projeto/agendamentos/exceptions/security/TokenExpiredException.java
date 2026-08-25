package com.projeto.agendamentos.exceptions.security;

public class TokenExpiredException extends SecurityException {
    public TokenExpiredException(String message) {
        super(message);
    }
}
