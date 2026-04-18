package com.spring.e_commerce.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Catch standard logic errors
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    }

    // Catch Database Unique Constraint errors (Like duplicate username)
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrity(org.springframework.dao.DataIntegrityViolationException e) {
        // If it's a duplicate key error from SQL Server
        if (e.getMessage() != null && e.getMessage().contains("duplicate key")) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username already exists"));
        }
        return ResponseEntity.badRequest().body(Map.of("message", "Database error occurred"));
    }
}