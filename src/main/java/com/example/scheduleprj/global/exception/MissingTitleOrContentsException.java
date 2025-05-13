package com.example.scheduleprj.global.exception;

public class MissingTitleOrContentsException extends RuntimeException {
    public MissingTitleOrContentsException() {
        super("제목과 내용은 필수 값입니다.");
    }
}