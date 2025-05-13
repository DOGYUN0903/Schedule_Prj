package com.example.scheduleprj.shcedule.dto;

import com.example.scheduleprj.shcedule.entity.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private Long memberId;
    private String title;
    private String contents;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime modifiedAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.memberId = schedule.getMemberId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.modifiedAt = schedule.getModifiedAt();
    }
}