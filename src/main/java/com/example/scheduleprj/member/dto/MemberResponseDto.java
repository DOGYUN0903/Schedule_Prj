package com.example.scheduleprj.member.dto;

import com.example.scheduleprj.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 회원 정보를 응답으로 전달하기 위한 DTO 클래스입니다.
 * - 서비스 또는 컨트롤러에서 Member 엔티티를 이 클래스로 변환하여 클라이언트에게 전달합니다.
 */
@Getter
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String name; // 회원 이름
    private String email; // 이메일
    @JsonFormat(pattern = "yyyy-MM-dd")  // 날짜 형식 지정하여 Json 응답에 적용
    private LocalDateTime modifiedAt; // 수정일

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.modifiedAt = member.getModifiedAt();
    }
}
