package com.example.scheduleprj.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 회원가입 요청 정보를 담는 DTO 클래스입니다.
 * - 각 필드에는 유효성 검증 어노테이션이 포함되어 있어, Controller에서 자동 검증이 가능합니다.
 */
@Getter
public class MemberRequestDto {

    /**
     * 회원 이름
     * - @NotBlank: null, 빈 문자열("") 또는 공백만 있는 문자열("   ") 허용하지 않음
     */
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    /**
     * 회원 이메일
     * - @NotBlank: null 또는 빈 값 불가
     * - @Email: 유효한 이메일 형식인지 검증 (예: user@example.com)
     */
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    /**
     * 비밀번호
     * - @NotBlank: null 또는 빈 값 불가
     */
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
