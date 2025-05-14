package com.example.scheduleprj.shcedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * 일정 생성/수정 요청 데이터를 담는 DTO 클래스입니다.
 * - 클라이언트로부터 title, contents, password, memberId를 전달받습니다.
 * - 각 필드에는 유효성 검사 어노테이션을 적용하여 입력값 검증을 수행합니다.
 */
@Getter
public class ScheduleRequestDto {

    /**
     * 일정 소유 회원 ID (외래키)
     * - null 값을 허용하지 않음 (@NotNull)
     */
    @NotNull(message = "memberId는 필수입니다.")
    private Long memberId; // 회원 id 외래키

    /**
     * 일정 제목
     * - 최소 한 글자 이상 필수 (@NotBlank)
     * - 최대 200자까지 허용 (@Size)
     */
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 200, message = "제목은 200자 이하로 입력해주세요.")
    private String title; // 제목

    /**
     * 일정 내용
     * - 최소 한 글자 이상 필수 (@NotBlank)
     */
    @NotBlank(message = "내용은 필수입니다.")
    private String contents; // 내용

    /**
     * 일정 작성/수정/삭제 시 검증에 사용할 비밀번호
     * - 최소 한 글자 이상 필수 (@NotBlank)
     */
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password; // 비밀번호
}
