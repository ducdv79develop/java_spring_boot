package com.local.ducdv.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ AppException.class })
    public ResponseEntity<String> handleException(AppException e) {
        return ResponseEntity.status(e.getCode()).body(e.getMessage());
    }

    @ExceptionHandler({ ValidateException.class })
    public ResponseEntity<String> handleValidateException(Exception e) {
        return ResponseEntity.status(422).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnwantedException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body("Server error");
    }
}
