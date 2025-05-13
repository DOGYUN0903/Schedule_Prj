package com.example.scheduleprj.shcedule.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private Long memberId; // 회원 id 외래키
    private String title; // 제목
    private String contents; // 내용
}
