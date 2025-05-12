package com.example.scheduleprj.lv1.repository;

import com.example.scheduleprj.lv1.dto.ScheduleResponseDto;
import com.example.scheduleprj.lv1.entity.Schedule;

import java.util.List;

public interface ScheduleRepositoryV1 {
    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules();
}
