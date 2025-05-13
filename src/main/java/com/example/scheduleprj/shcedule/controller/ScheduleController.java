package com.example.scheduleprj.shcedule.controller;

import com.example.scheduleprj.member.entity.Member;
import com.example.scheduleprj.member.service.MemberService;
import com.example.scheduleprj.shcedule.dto.ScheduleRequestDto;
import com.example.scheduleprj.shcedule.dto.ScheduleResponseDto;
import com.example.scheduleprj.shcedule.service.ScheduleServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleServiceV1 scheduleServiceV1;
    private final MemberService memberService;


    /**
     * 일정 생성 API
     * 회원 Id를 받아서 일정을 생성한다.
     * @param requestDto
     * @return
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return new ResponseEntity<>(scheduleServiceV1.saveSchedule(requestDto), HttpStatus.CREATED);
    }


    /**
     * 전체 일정 조회 API
     * @param memberId
     * @param modifiedAt
     * @return
     */
    @GetMapping
    public List<ScheduleResponseDto> findAllSchedules(@RequestParam(name = "memberId", required = false) Long memberId,
                                                      @RequestParam(name = "modifiedAt", required = false) String modifiedAt) {
        return scheduleServiceV1.findAllSchedules(memberId, modifiedAt);
    }


    /**
     * 세부 일정 조회 API
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findSchedulesById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(scheduleServiceV1.findScheduleById(id), HttpStatus.OK);
    }


    /**
     * 선택 일정 수정 API
     * @param id
     * @param requestDto
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable("id") Long id,
                                                              @RequestBody ScheduleRequestDto requestDto) {
        return new ResponseEntity<>(scheduleServiceV1.updateSchedule(id, requestDto.getTitle(), requestDto.getContents(), requestDto.getPassword()), HttpStatus.OK);
    }


//    /**
//     *  삭제 API
//     * @param id
//     * @return
//     */
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ScheduleResponseDto> deleteSchedule(@PathVariable("id") Long id,
//                                                              @RequestParam("password") String password) {
//        scheduleServiceV1.deleteSchedule(id, password);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
