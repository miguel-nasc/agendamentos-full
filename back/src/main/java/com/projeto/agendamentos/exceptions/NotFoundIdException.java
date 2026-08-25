package com.projeto.agendamentos.exceptions;

public class NotFoundIdException extends RuntimeException {
    public NotFoundIdException(String message) {
        super(message);
    }

}
