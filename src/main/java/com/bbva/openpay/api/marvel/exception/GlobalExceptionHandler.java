package com.bbva.openpay.api.marvel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

//Author: Jose Calderon
@ControllerAdvice
public class GlobalExceptionHandler {
    public static final String ERROR = "Error al procesar la respuesta de Marvel:";

    @ExceptionHandler(MarvelException.class)
    public ResponseEntity<Object> handleAccuweatherException(MarvelException ex) {
        // Devuelve una respuesta personalizada con el mensaje de error
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap(ERROR, ex.getMessage()));
    }
}
