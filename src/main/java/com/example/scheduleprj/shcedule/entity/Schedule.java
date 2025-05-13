package com.example.scheduleprj.shcedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Schedule {
    private Long id;
    private String writer;
    private String title;
    private String password;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public Schedule(String writer, String title, String password, String contents) {
        this.writer = writer;
        this.title = title;
        this.password = password;
        this.contents = contents;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = this.createdAt;
    }

}
