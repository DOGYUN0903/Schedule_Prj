package com.example.scheduleprj.global.exception;

public class NoScheduleModifiedException extends RuntimeException {
    public NoScheduleModifiedException(Long id) {
        super("일정(id = " + id + ")은 존재하지만 수정된 내용이 없습니다.");
    }
}
