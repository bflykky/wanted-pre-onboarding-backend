package com.wanted.preonboarding.controller;

import com.wanted.preonboarding.dto.member.MemberJoinRequestDto;
import com.wanted.preonboarding.dto.member.MemberLoginRequestDto;
import com.wanted.preonboarding.dto.member.MemberJoinResponseDto;
import com.wanted.preonboarding.dto.member.MemberLoginResponseDto;
import com.wanted.preonboarding.result.ResultCode;
import com.wanted.preonboarding.result.ResultResponse;
import com.wanted.preonboarding.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping(value = "/members")
    public ResultResponse join(@Valid @RequestBody MemberJoinRequestDto memberJoinRequestDto) {
        String email = memberJoinRequestDto.getEmail();
        String password = memberJoinRequestDto.getPassword();
        MemberJoinResponseDto memberJoinResponseDto = memberService.join(email, password);
        return ResultResponse.of(ResultCode.MEMBER_JOIN_SUCCESS, memberJoinResponseDto);
    }

    @PostMapping(value = "/members/login")
    public ResultResponse login(@Valid @RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        String email = memberLoginRequestDto.getEmail();
        String password = memberLoginRequestDto.getPassword();
        // 실제 등록된 회원인지 email을 통해 검색.
        // 등록된 회원의 비밀번호와 일치하는지 확인.
        MemberLoginResponseDto memberLoginResponseDto = memberService.login(email, password);
        // Access Token인 JWT 발급
        return ResultResponse.of(ResultCode.MEMBER_LOGIN_SUCCESS, memberLoginResponseDto);
    }
}
