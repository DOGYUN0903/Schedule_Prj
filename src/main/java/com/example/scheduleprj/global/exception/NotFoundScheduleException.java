package com.example.scheduleprj.global.exception;

/**
 * 주어진 ID에 해당하는 일정이 존재하지 않을 때 발생하는 예외
 */
public class NotFoundScheduleException extends RuntimeException {
    public NotFoundScheduleException(Long id) {
        super("해당 일정이 존재하지 않습니다. id = " + id);
    }
}
