package com.tbp.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(GameNotFoundException ex) {
        var body = new ErrorResponse("GAME_NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(404).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        var body = new ErrorResponse("INTERNAL_ERROR", "Something went wrong");
        return ResponseEntity.status(500).body(body);
    }
}
