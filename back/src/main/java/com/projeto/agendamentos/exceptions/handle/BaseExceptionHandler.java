package com.projeto.agendamentos.exceptions.handle;

import com.projeto.agendamentos.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

public abstract class BaseExceptionHandler {

    protected ResponseEntity<ExceptionResponse> buildResponse(
            Exception ex,
            WebRequest request,
            HttpStatus status
    ) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return ResponseEntity.status(status).body(response);
    }
}