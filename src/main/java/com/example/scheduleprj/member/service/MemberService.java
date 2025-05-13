package com.example.scheduleprj.member.service;

import com.example.scheduleprj.member.dto.MemberRequestDto;
import com.example.scheduleprj.member.dto.MemberResponseDto;

public interface MemberService {

    MemberResponseDto createMember(MemberRequestDto requestDto);

    MemberResponseDto findMemberById(Long id);
}
