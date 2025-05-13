package com.example.scheduleprj.shcedule.service;

import com.example.scheduleprj.member.entity.Member;
import com.example.scheduleprj.member.repository.MemberRepository;
import com.example.scheduleprj.shcedule.dto.ScheduleRequestDto;
import com.example.scheduleprj.shcedule.dto.ScheduleResponseDto;
import com.example.scheduleprj.shcedule.entity.Schedule;
import com.example.scheduleprj.shcedule.repository.ScheduleRepositoryV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceV1Impl implements ScheduleServiceV1{

    private final ScheduleRepositoryV1 scheduleRepositoryV1;
    private final MemberRepository memberRepository;

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {
        Member memberByIdOrElseThrow = memberRepository.findMemberByIdOrElseThrow(requestDto.getMemberId());
        if (!requestDto.getPassword().equals(memberByIdOrElseThrow.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is not the same");
        }

        Schedule schedule = new Schedule(
                requestDto.getMemberId(),
                requestDto.getTitle(),
                requestDto.getContents()
        );

        return scheduleRepositoryV1.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(Long memberId, String modifiedAt) {
        return scheduleRepositoryV1.findAllSchedules(memberId, modifiedAt);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = scheduleRepositoryV1.findScheduleByIdOrElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, String title, String contents, String password) {
        Member memberByIdOrElseThrow = memberRepository.findMemberByIdOrElseThrow(scheduleRepositoryV1.findScheduleByIdOrElseThrow(id).getMemberId());
        if (!memberByIdOrElseThrow.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is not the same");
        }

        if (title == null || contents == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and contents are required values.");
        }

        int updateRow = scheduleRepositoryV1.updateSchedule(id, title, contents);
        if (updateRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data has been modified.");
        }

        return new ScheduleResponseDto(scheduleRepositoryV1.findScheduleByIdOrElseThrow(id));
    }

//    @Override
//    public void deleteSchedule(Long id, String password) {
//        Schedule findSchedule = scheduleRepositoryV1.findScheduleByIdOrElseThrow(id);
//        if (!findSchedule.getPassword().equals(password)) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is not the same");
//        }
//
//        int deleteRow = scheduleRepositoryV1.deleteSchedule(id);
//        if (deleteRow == 0) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
//        }
//    }
}
