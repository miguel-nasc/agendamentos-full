package com.projeto.agendamentos.exceptions.handle;

import com.projeto.agendamentos.exceptions.ExceptionResponse;
import com.projeto.agendamentos.exceptions.user.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class UserExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ExceptionResponse> handleUserException(
            UserException ex,
            WebRequest request
    ) {
        return buildResponse(
                ex,
                request,
                HttpStatus.NOT_FOUND
        );
    }


}
