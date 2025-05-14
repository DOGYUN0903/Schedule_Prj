package com.example.scheduleprj.global.exception;

/**
 * 일정 수정 요청은 있었으나, 실제 변경된 내용이 없을 때 발생하는 예외
 */
public class NoScheduleModifiedException extends RuntimeException {
    public NoScheduleModifiedException(Long id) {
        super("일정(id = " + id + ")은 존재하지만 수정된 내용이 없습니다.");
    }
}
