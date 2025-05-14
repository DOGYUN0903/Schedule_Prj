package com.example.scheduleprj.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 전역 예외 처리 클래스
 * - 커스텀 예외 및 유효성 검증 실패에 대한 공통 응답 포맷 제공
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 커스텀 예외 처리 핸들러
     * - 비밀번호 오류, 일정 없음, 내용 없음, 수정 실패
     */
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

        // 공통 응답 포맷 구성
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", e.getMessage());

        return ResponseEntity.status(status).body(body);
    }

    /**
     * @Valid 실패
     * - 필드명과 에러 메시지를 합쳐서 반환
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation Failed");
        body.put("message", message);

        return ResponseEntity.badRequest().body(body);
    }
}
