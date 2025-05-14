package com.example.scheduleprj.global.exception;

/**
 * 비밀번호가 일치하지 않을 경우 발생하는 예외
 * - 일정 생성/수정/삭제 시 사용
 */
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
