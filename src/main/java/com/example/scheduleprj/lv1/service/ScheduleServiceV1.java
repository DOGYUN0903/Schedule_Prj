package com.example.scheduleprj.lv1.service;

import com.example.scheduleprj.lv1.dto.ScheduleRequestDto;
import com.example.scheduleprj.lv1.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleServiceV1 {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);

    List<ScheduleResponseDto> findAllSchedules(String writer, String modifiedAt);

    ScheduleResponseDto findScheduleById(Long id);

    ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto);
}
