package com.example.scheduleprj.shcedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Schedule {
    private Long id;
    private Long memberId; // 회원 id 외래키
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public Schedule(Long memberId, String title, String contents) {
        this.memberId = memberId;
        this.title = title;
        this.contents = contents;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = this.createdAt;
    }

}
