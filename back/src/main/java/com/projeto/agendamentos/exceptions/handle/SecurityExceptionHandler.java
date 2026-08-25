package com.projeto.agendamentos.exceptions.handle;

import com.projeto.agendamentos.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class SecurityExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ExceptionResponse> handleUserException(
            SecurityException ex,
            WebRequest request
    ) {
        return buildResponse(
                ex,
                request,
                HttpStatus.FORBIDDEN
        );
    }
}
