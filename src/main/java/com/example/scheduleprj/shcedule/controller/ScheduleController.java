package com.example.scheduleprj.shcedule.controller;

import com.example.scheduleprj.shcedule.dto.ScheduleRequestDto;
import com.example.scheduleprj.shcedule.dto.ScheduleResponseDto;
import com.example.scheduleprj.shcedule.service.ScheduleServiceV1;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 일정(Schedule) 관련 HTTP 요청을 처리하는 REST 컨트롤러입니다.
 * - 생성, 조회, 수정, 삭제 기능을 제공합니다.
 */
@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor // 생성자 주입 자동 생성 (final 필드만)
public class ScheduleController {

    private final ScheduleServiceV1 scheduleServiceV1;


    /**
     * [POST] /schedules
     * 일정 생성 API
     * - 회원 ID와 제목/내용/비밀번호를 포함한 일정 정보를 받아 DB에 저장합니다.
     *
     * @param requestDto 일정 생성에 필요한 정보(title, contents, password, memberId)
     * @return 생성된 일정 정보 (201 CREATED)
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody @Valid ScheduleRequestDto requestDto) {
        return new ResponseEntity<>(scheduleServiceV1.saveSchedule(requestDto), HttpStatus.CREATED);
    }


    /**
     * [GET] /schedules
     * 전체 일정 조회 API
     * - 조건: (선택) 회원 ID, 수정일 필터링
     * - 페이징: page (기본값 0), size (기본값 10)
     *
     * @param memberId    조회 대상 회원 ID (nullable)
     * @param modifiedAt  수정일 조건 (nullable)
     * @param page        페이지 번호
     * @param size        페이지 크기
     * @return 일정 리스트 (ScheduleResponseDto)
     */
    @GetMapping
    public List<ScheduleResponseDto> findAllSchedules(@RequestParam(name = "memberId", required = false) Long memberId,
                                                      @RequestParam(name = "modifiedAt", required = false) String modifiedAt,
                                                      @RequestParam(name = "page", defaultValue = "0") int page,
                                                      @RequestParam(name = "size", defaultValue = "10") int size) {
        return scheduleServiceV1.findAllSchedules(memberId, modifiedAt, page, size);
    }


    /**
     * [GET] /schedules/{id}
     * 세부 일정 단건 조회 API
     *
     * @param id 일정 ID
     * @return 해당 일정 정보 (200 OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findSchedulesById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(scheduleServiceV1.findScheduleById(id), HttpStatus.OK);
    }


    /**
     * [PATCH] /schedules/{id}
     * 선택 일정 수정 API
     * - 제목, 내용을 수정합니다.
     *
     * @param id         수정할 일정 ID
     * @param requestDto 수정에 필요한 데이터(title, contents, password)
     * @return 수정된 일정 정보 (200 OK)
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable("id") Long id,
                                                              @RequestBody @Valid ScheduleRequestDto requestDto) {
        return new ResponseEntity<>(scheduleServiceV1.updateSchedule(id, requestDto.getTitle(), requestDto.getContents(), requestDto.getPassword()), HttpStatus.OK);
    }


    /**
     * [DELETE] /schedules/{id}?password=xxx
     * 일정 삭제 API
     * - 비밀번호가 일치할 경우 해당 일정을 삭제합니다.
     *
     * @param id        삭제할 일정 ID
     * @param password  삭제 시 검증할 비밀번호
     * @return 200 OK (성공적으로 삭제된 경우)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> deleteSchedule(@PathVariable("id") Long id,
                                                              @RequestParam("password") String password) {
        scheduleServiceV1.deleteSchedule(id, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
