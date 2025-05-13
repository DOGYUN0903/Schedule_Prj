package com.example.scheduleprj.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberRequestDto {

    /**
     * NotBlank : 해당 필드에 null, "", " " 을 불허한다. 즉 최소 1글자
     * Email : 해당 필드가 이메일 형식을 가지도록 검증한다.
     */

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
