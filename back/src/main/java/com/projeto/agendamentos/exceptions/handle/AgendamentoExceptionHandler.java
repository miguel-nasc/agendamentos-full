package com.projeto.agendamentos.exceptions.handle;

import com.projeto.agendamentos.exceptions.ExceptionResponse;
import com.projeto.agendamentos.exceptions.agendamentos.AgendamentoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class AgendamentoExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler(AgendamentoException.class)
    public ResponseEntity<ExceptionResponse> handleUserException(
            AgendamentoException ex,
            WebRequest request
    ) {
        return buildResponse(
                ex,
                request,
                HttpStatus.BAD_REQUEST
        );
    }
}
