package com.example.scheduleprj.shcedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    /**
     * NotNull : 해당 필드에 null값을 불허한다.
     * NotBlank : 해당 필드에 null, "", " " 을 불허한다. 즉 최소 1글자
     * Size : 문자열의 최소, 최대 크기를 검증한다.
     */

    @NotNull(message = "memberId는 필수입니다.")
    private Long memberId; // 회원 id 외래키

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 200, message = "제목은 200자 이하로 입력해주세요.")
    private String title; // 제목

    @NotBlank(message = "내용은 필수입니다.")
    private String contents; // 내용

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password; // 비밀번호
}
