package com.example.scheduleprj.global.exception;

/**
 * 일정 수정 시 제목이나 내용이 누락된 경우 발생하는 예외
 */
public class MissingTitleOrContentsException extends RuntimeException {
    public MissingTitleOrContentsException() {
        super("제목과 내용은 필수 값입니다.");
    }
}