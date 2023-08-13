package com.wanted.preonboarding.controller;

import com.wanted.preonboarding.dto.member.MemberJoinRequestDto;
import com.wanted.preonboarding.dto.member.MemberLoginRequestDto;
import com.wanted.preonboarding.dto.member.MemberJoinResponseDto;
import com.wanted.preonboarding.jwt.provider.JwtProvider;
import com.wanted.preonboarding.result.ResultCode;
import com.wanted.preonboarding.result.ResultResponse;
import com.wanted.preonboarding.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final JwtProvider jwtProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
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
        return ResultResponse.of(ResultCode.MEMBER_LOGIN_SUCCESS);
    }
}
