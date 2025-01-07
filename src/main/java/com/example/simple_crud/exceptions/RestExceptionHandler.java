package com.example.simple_crud.exceptions;

import com.example.simple_crud.common.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

@ControllerAdvice
public class RestExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class.getName());

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Response> handleException(Exception ex, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        if (ex instanceof ResourceNotFoundException || ex instanceof NoSuchElementException)
            return new ResponseEntity<>(new Response(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
                    HttpStatus.NOT_FOUND);
        else if (ex instanceof ResourceForbiddenException)
            return new ResponseEntity<>(new Response(HttpStatus.FORBIDDEN.value(), ex.getMessage()),
                    HttpStatus.FORBIDDEN);
        else if (ex instanceof BadRequestException)
            return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
                    HttpStatus.BAD_REQUEST);
        else if (ex instanceof UnauthorizedException)
            return new ResponseEntity<>(new Response(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()),
                    HttpStatus.UNAUTHORIZED);
        else {
            return new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error! please contact your administrator."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
