package com.example.scheduleprj.lv1.repository;

import com.example.scheduleprj.lv1.dto.ScheduleResponseDto;
import com.example.scheduleprj.lv1.entity.Schedule;

public interface ScheduleRepositoryV1 {
    ScheduleResponseDto saveSchedule(Schedule schedule);
}
