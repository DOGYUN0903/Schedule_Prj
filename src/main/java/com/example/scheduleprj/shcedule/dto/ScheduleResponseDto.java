package com.example.scheduleprj.shcedule.dto;

import com.example.scheduleprj.shcedule.entity.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 일정 조회 응답을 위한 DTO 클래스입니다.
 * - 클라이언트에게 반환할 일정 정보를 담습니다.
 * - 날짜 포맷, 엔티티 → DTO 변환 생성자 등을 포함합니다.
 */
@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private Long memberId; // 회원 ID
    private String title; // 제목
    private String contents; // 내용
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime modifiedAt; // 수정일

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.memberId = schedule.getMemberId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.modifiedAt = schedule.getModifiedAt();
    }
}