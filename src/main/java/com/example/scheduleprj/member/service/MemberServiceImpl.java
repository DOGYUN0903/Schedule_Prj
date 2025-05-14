package com.example.scheduleprj.member.service;

import com.example.scheduleprj.member.dto.MemberRequestDto;
import com.example.scheduleprj.member.dto.MemberResponseDto;
import com.example.scheduleprj.member.entity.Member;
import com.example.scheduleprj.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * 회원 관련 비즈니스 로직을 처리하는 서비스 구현 클래스입니다.
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;


    /**
     * 회원 생성
     * - 요청 DTO를 기반으로 Member 엔티티를 생성한 후,
     * - Repository를 통해 DB에 저장하고, 결과를 응답 DTO로 변환해 반환합니다.
     *
     * @param requestDto 클라이언트로부터 받은 회원가입 정보
     * @return 생성된 회원 정보를 담은 응답 DTO
     */
    @Override
    public MemberResponseDto createMember(MemberRequestDto requestDto) {
        // DTO -> Entity
        Member member = new Member(
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );

        // DB에 저장 후 결과 DTO 반환
        return memberRepository.createMember(member);
    }

    /**
     * 회원 정보 조회 (ID 기반)
     * - Repository에서 해당 ID의 Member를 조회한 뒤,
     * - 응답 DTO로 변환하여 반환합니다.
     *
     * @param id 조회할 회원 ID
     * @return 해당 회원의 응답 DTO
     */
    @Override
    public MemberResponseDto findMemberById(Long id) {
        Member member = memberRepository.findMemberByIdOrElseThrow(id);

        return new MemberResponseDto(member);
    }
}
