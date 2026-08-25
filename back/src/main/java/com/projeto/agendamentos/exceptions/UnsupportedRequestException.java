package com.projeto.agendamentos.exceptions;

public class UnsupportedRequestException extends RuntimeException {
    public UnsupportedRequestException(String message) {
        super(message);
    }
}
