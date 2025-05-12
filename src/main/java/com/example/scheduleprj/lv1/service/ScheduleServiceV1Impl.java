package com.example.scheduleprj.lv1.service;

import com.example.scheduleprj.lv1.dto.ScheduleRequestDto;
import com.example.scheduleprj.lv1.dto.ScheduleResponseDto;
import com.example.scheduleprj.lv1.entity.Schedule;
import com.example.scheduleprj.lv1.repository.ScheduleRepositoryV1;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceV1Impl implements ScheduleServiceV1{

    private final ScheduleRepositoryV1 scheduleRepositoryV1;

    public ScheduleServiceV1Impl(ScheduleRepositoryV1 scheduleRepositoryV1) {
        this.scheduleRepositoryV1 = scheduleRepositoryV1;
    }


    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(
                requestDto.getWriter(),
                requestDto.getTitle(),
                requestDto.getPassword(),
                requestDto.getContents()
        );

        return scheduleRepositoryV1.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return scheduleRepositoryV1.findAllSchedules();
    }
}
