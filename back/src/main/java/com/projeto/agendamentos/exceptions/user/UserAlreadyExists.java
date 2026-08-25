package com.projeto.agendamentos.exceptions.user;

public class UserAlreadyExists extends UserException {
    public UserAlreadyExists(String message) {
        super(message);
    }
}
