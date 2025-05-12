package com.example.scheduleprj.lv1.service;

import com.example.scheduleprj.lv1.dto.ScheduleRequestDto;
import com.example.scheduleprj.lv1.dto.ScheduleResponseDto;

public interface ScheduleServiceV1 {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);
}
