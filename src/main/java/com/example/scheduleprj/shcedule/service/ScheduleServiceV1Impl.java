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

/**
 * 일정 관련 비즈니스 로직을 처리하는 서비스 구현체입니다.
 * - 생성, 전체 조회, 단건 조회, 수정, 삭제 기능을 제공합니다.
 */
@Service
@RequiredArgsConstructor
public class ScheduleServiceV1Impl implements ScheduleServiceV1{

    private final ScheduleRepositoryV1 scheduleRepositoryV1;
    private final MemberRepository memberRepository;

    /**
     * 일정 생성
     * - 회원 존재 및 비밀번호 검증 후, 일정 저장
     */
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {
        // 회원 조회 및 비밀번호 검증
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

    /**
     * 전체 일정 조회
     * - 조건: 회원 ID, 수정일(옵션), 페이징 포함
     */
    @Override
    public List<ScheduleResponseDto> findAllSchedules(Long memberId, String modifiedAt, int page, int size) {
        return scheduleRepositoryV1.findAllSchedules(memberId, modifiedAt, page, size);
    }

    /**
     * 일정 단건 조회
     * - 존재하지 않을 경우 내부에서 예외 발생
     */
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = scheduleRepositoryV1.findScheduleByIdOrElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    /**
     * 일정 수정
     * - 비밀번호 검증
     * - 제목/내용 null 체크
     * - 수정된 행이 없으면 예외 발생
     */
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

    /**
     * 일정 삭제
     * - 비밀번호 검증 후 삭제
     * - 삭제된 행이 없으면 예외 발생
     */
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
