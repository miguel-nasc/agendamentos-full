package com.projeto.agendamentos.exceptions.handle;

import com.projeto.agendamentos.exceptions.ExceptionResponse;
import com.projeto.agendamentos.exceptions.InvalidRequestException;
import com.projeto.agendamentos.exceptions.NotFoundIdException;
import com.projeto.agendamentos.exceptions.UnsupportedRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
public class GlobalEntityResponseHandler extends BaseExceptionHandler {

    @ExceptionHandler(UnsupportedRequestException.class)
    public ResponseEntity<ExceptionResponse> handleUnsupportedRequest(
            UnsupportedRequestException ex,
            WebRequest request
    ) {
        return buildResponse(
                ex,
                request,
                HttpStatus.NOT_FOUND
        );
    }


    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidRequest(
            InvalidRequestException ex,
            WebRequest request
    ) {
        return buildResponse(
                ex,
                request,
                HttpStatus.BAD_REQUEST
        );
    }

     @ExceptionHandler(NotFoundIdException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundId(
            NotFoundIdException ex,
            WebRequest request
    ) {
        return buildResponse(
                ex,
                request,
                HttpStatus.NOT_FOUND
        );
    }




}
