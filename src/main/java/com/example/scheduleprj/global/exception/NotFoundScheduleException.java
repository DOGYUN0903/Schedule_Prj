package com.example.scheduleprj.global.exception;

public class NotFoundScheduleException extends RuntimeException {
    public NotFoundScheduleException(Long id) {
        super("해당 일정이 존재하지 않습니다. id = " + id);
    }
}
