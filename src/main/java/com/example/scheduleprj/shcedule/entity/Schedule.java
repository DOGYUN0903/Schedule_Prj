package com.example.scheduleprj.shcedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 일정(Schedule)을 표현하는 도메인 엔티티 클래스입니다.
 */
@AllArgsConstructor
@Getter
public class Schedule {
    private Long id;
    private Long memberId; // 회원 id 외래키
    private String title; // 제목
    private String contents; // 내용
    private LocalDateTime createdAt; // 생성일
    private LocalDateTime modifiedAt; // 수정일

    public Schedule(Long memberId, String title, String contents) {
        this.memberId = memberId;
        this.title = title;
        this.contents = contents;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = this.createdAt;
    }

}
