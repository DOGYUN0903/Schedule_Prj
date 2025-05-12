package com.example.scheduleprj.lv1.service;

import com.example.scheduleprj.lv1.dto.ScheduleRequestDto;
import com.example.scheduleprj.lv1.dto.ScheduleResponseDto;
import com.example.scheduleprj.lv1.entity.Schedule;
import com.example.scheduleprj.lv1.repository.ScheduleRepositoryV1;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
    public List<ScheduleResponseDto> findAllSchedules(String Writer, String modifiedAt) {
        return scheduleRepositoryV1.findAllSchedules(Writer, modifiedAt);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = scheduleRepositoryV1.findScheduleByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepositoryV1.findScheduleByIdOrElseThrow(id);

        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        Schedule updated = new Schedule(
                schedule.getId(),
                requestDto.getWriter(),
                requestDto.getTitle(),
                schedule.getPassword(),
                schedule.getContents(),
                schedule.getCreatedAt(),
                LocalDateTime.now()
        );

        return scheduleRepositoryV1.updateSchedule(updated);
    }
}
