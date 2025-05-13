package com.example.scheduleprj.lv1.service;

import com.example.scheduleprj.lv1.dto.ScheduleRequestDto;
import com.example.scheduleprj.lv1.dto.ScheduleResponseDto;
import com.example.scheduleprj.lv1.entity.Schedule;
import com.example.scheduleprj.lv1.repository.ScheduleRepositoryV1;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
    public ScheduleResponseDto updateSchedule(Long id, String writer, String contents, String password) {
        Schedule schedule = scheduleRepositoryV1.findScheduleByIdOrElseThrow(id);

        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is not the same");
        }

        if (writer == null || contents == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The writer and contents are required values.");
        }

        int updateRow = scheduleRepositoryV1.updateSchedule(id, writer, contents);
        if (updateRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data has been modified.");
        }

        return new ScheduleResponseDto(scheduleRepositoryV1.findScheduleByIdOrElseThrow(id));
    }

    @Override
    public void deleteSchedule(Long id, String password) {
        Schedule findSchedule = scheduleRepositoryV1.findScheduleByIdOrElseThrow(id);
        if (!findSchedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is not the same");
        }

        int deleteRow = scheduleRepositoryV1.deleteSchedule(id);
        if (deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }
}
