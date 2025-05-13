package com.example.scheduleprj.member.repository;

import com.example.scheduleprj.member.dto.MemberRequestDto;
import com.example.scheduleprj.member.dto.MemberResponseDto;
import com.example.scheduleprj.member.entity.Member;

public interface MemberRepository {

    MemberResponseDto createMember(Member member);
}
