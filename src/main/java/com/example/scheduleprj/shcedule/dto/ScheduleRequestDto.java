package com.example.scheduleprj.shcedule.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String title; // 제목
    private String writer; // 작성자
    private String password; // 비밀번호
    private String contents; // 내용
}
