package com.example.scheduleprj.shcedule.repository;

import com.example.scheduleprj.shcedule.dto.ScheduleResponseDto;
import com.example.scheduleprj.shcedule.entity.Schedule;

import java.util.List;

public interface ScheduleRepositoryV1 {
    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules(Long memberId, String modifiedAt);
//
    Schedule findScheduleByIdOrElseThrow(Long id);
//
    int updateSchedule(Long id, String title, String contents);
//
//    int deleteSchedule(Long id);
}
