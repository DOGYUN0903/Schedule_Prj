package com.example.scheduleprj.shcedule.repository;

import com.example.scheduleprj.shcedule.dto.ScheduleResponseDto;
import com.example.scheduleprj.shcedule.entity.Schedule;

import java.util.List;

public interface ScheduleRepositoryV1 {
    ScheduleResponseDto saveSchedule(Schedule schedule);

//    List<ScheduleResponseDto> findAllSchedules(String writer, String modifiedAt);
//
//    Schedule findScheduleByIdOrElseThrow(Long id);
//
//    int updateSchedule(Long id, String writer, String contents);
//
//    int deleteSchedule(Long id);
}
