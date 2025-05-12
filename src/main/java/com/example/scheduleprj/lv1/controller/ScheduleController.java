package com.example.scheduleprj.lv1.controller;

import com.example.scheduleprj.lv1.dto.ScheduleRequestDto;
import com.example.scheduleprj.lv1.dto.ScheduleResponseDto;
import com.example.scheduleprj.lv1.service.ScheduleServiceV1;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule-v1")
public class ScheduleController {

    private final ScheduleServiceV1 scheduleServiceV1;

    public ScheduleController(ScheduleServiceV1 scheduleServiceV1) {
        this.scheduleServiceV1 = scheduleServiceV1;
    }

    // 일정 생성 API
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return new ResponseEntity<>(scheduleServiceV1.saveSchedule(requestDto), HttpStatus.CREATED);
    }

    // 전체 일정 조회 API
    @GetMapping
    public List<ScheduleResponseDto> findAllSchedules(@RequestParam(name = "writer", required = false) String writer,
                                                      @RequestParam(name = "modifiedAt", required = false) String modifiedAt) {
        return scheduleServiceV1.findAllSchedules(writer, modifiedAt);
    }

    // 세부 일정 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findSchedulesById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(scheduleServiceV1.findScheduleById(id), HttpStatus.OK);
    }

    // 선택 일정 수정 API(

}
