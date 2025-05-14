package com.example.scheduleprj.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 회원 정보를 나타내는 도메인 엔티티 클래스입니다.
 */
@Getter
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자 생성
public class Member {
    private Long id;
    private String name; // 이름
    private String email; // 이메일
    private String password; // 비밀번호
    private LocalDateTime createdAt; // 생성일
    private LocalDateTime modifiedAt; // 수정일

    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = this.createdAt;
    }
}
