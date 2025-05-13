package com.example.scheduleprj.shcedule.service;

import com.example.scheduleprj.shcedule.dto.ScheduleRequestDto;
import com.example.scheduleprj.shcedule.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleServiceV1 {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);

    List<ScheduleResponseDto> findAllSchedules(Long memberId, String modifiedAt, int page, int size);

    ScheduleResponseDto findScheduleById(Long id);

    ScheduleResponseDto updateSchedule(Long id, String title, String contents, String password);

    void deleteSchedule(Long id, String password);

}
