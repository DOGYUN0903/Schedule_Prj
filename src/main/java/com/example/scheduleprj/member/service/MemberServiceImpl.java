package com.example.scheduleprj.member.service;

import com.example.scheduleprj.member.dto.MemberRequestDto;
import com.example.scheduleprj.member.dto.MemberResponseDto;
import com.example.scheduleprj.member.entity.Member;
import com.example.scheduleprj.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberResponseDto createMember(MemberRequestDto requestDto) {
        Member member = new Member(
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );
        return memberRepository.createMember(member);
    }
}
