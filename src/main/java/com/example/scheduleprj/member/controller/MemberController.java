package com.example.scheduleprj.member.controller;

import com.example.scheduleprj.member.dto.MemberRequestDto;
import com.example.scheduleprj.member.dto.MemberResponseDto;
import com.example.scheduleprj.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 회원 관련 요청을 처리하는 컨트롤러 클래스입니다.
 * - 회원 가입
 * - 회원 단건 조회
 */
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor // final이 붙어있는 필드의 생성자 자동 생성
public class MemberController {

    private final MemberService memberService;

    /**
     * [POST] /members
     * 회원 가입 요청을 처리합니다.
     *
     * @param requestDto 클라이언트로부터 전달받은 회원가입 정보 (name, email, password)
     * @return 생성된 회원 정보를 담은 응답 DTO (201 CREATED 상태로 반환)
     */
    @PostMapping
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody @Valid MemberRequestDto requestDto) {
        return new ResponseEntity<>(memberService.createMember(requestDto), HttpStatus.CREATED);
    }

    /**
     * [GET] /members/{id}
     * ID로 회원 정보를 조회합니다.
     *
     * @param id 조회할 회원의 고유 식별자
     * @return 해당 회원의 상세 정보를 담은 응답 DTO (200 OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findMemberById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(memberService.findMemberById(id), HttpStatus.OK);
    }
}
