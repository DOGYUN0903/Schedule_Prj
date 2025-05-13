package com.example.scheduleprj.shcedule.service;

import com.example.scheduleprj.global.exception.InvalidPasswordException;
import com.example.scheduleprj.global.exception.MissingTitleOrContentsException;
import com.example.scheduleprj.global.exception.NoScheduleModifiedException;
import com.example.scheduleprj.global.exception.NotFoundScheduleException;
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
            throw new InvalidPasswordException();
        }

        Schedule schedule = new Schedule(
                requestDto.getMemberId(),
                requestDto.getTitle(),
                requestDto.getContents()
        );

        return scheduleRepositoryV1.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(Long memberId, String modifiedAt, int page, int size) {
        return scheduleRepositoryV1.findAllSchedules(memberId, modifiedAt, page, size);
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
            throw new InvalidPasswordException();
        }

        if (title == null || contents == null) {
            throw new MissingTitleOrContentsException();
        }

        int updateRow = scheduleRepositoryV1.updateSchedule(id, title, contents);
        if (updateRow == 0) {
            throw new NoScheduleModifiedException(id);
        }

        return new ScheduleResponseDto(scheduleRepositoryV1.findScheduleByIdOrElseThrow(id));
    }

    @Override
    public void deleteSchedule(Long id, String password) {
        Member memberByIdOrElseThrow = memberRepository.findMemberByIdOrElseThrow(scheduleRepositoryV1.findScheduleByIdOrElseThrow(id).getMemberId());
        if (!memberByIdOrElseThrow.getPassword().equals(password)) {
            throw new InvalidPasswordException();
        }

        int deleteRow = scheduleRepositoryV1.deleteSchedule(id);
        if (deleteRow == 0) {
            throw new NotFoundScheduleException(id);
        }
    }
}
