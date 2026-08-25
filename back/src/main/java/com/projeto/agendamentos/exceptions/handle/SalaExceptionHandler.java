package com.projeto.agendamentos.exceptions.handle;

import com.projeto.agendamentos.exceptions.ExceptionResponse;
import com.projeto.agendamentos.exceptions.sala.SalaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class SalaExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler(SalaException.class)
    public ResponseEntity<ExceptionResponse> handleUserException(
            SalaException ex,
            WebRequest request
    ) {
        return buildResponse(
                ex,
                request,
                HttpStatus.NOT_FOUND
        );
    }
}
