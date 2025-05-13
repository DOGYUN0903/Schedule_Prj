package com.example.scheduleprj.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            InvalidPasswordException.class,
            NotFoundScheduleException.class,
            MissingTitleOrContentsException.class,
            NoScheduleModifiedException.class
    })
    public ResponseEntity<Map<String, Object>> handleCustomExceptions(RuntimeException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (e instanceof NotFoundScheduleException) {
            status = HttpStatus.NOT_FOUND;
        }

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", e.getMessage());

        return ResponseEntity.status(status).body(body);
    }
}
