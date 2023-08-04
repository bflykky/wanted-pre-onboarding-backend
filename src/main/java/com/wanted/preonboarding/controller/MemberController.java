package com.wanted.preonboarding.controller;

import com.wanted.preonboarding.dto.MemberRegisterRequestDto;
import com.wanted.preonboarding.dto.MemberRegisterResponseDto;
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
    public ResultResponse register(@Valid @RequestBody MemberRegisterRequestDto memberRegisterRequestDto) {
        String email = memberRegisterRequestDto.getEmail();
        String password = memberRegisterRequestDto.getPassword();
        MemberRegisterResponseDto memberRegisterResponseDto = memberService.register(email, password);
        return ResultResponse.of(ResultCode.MEMBER_REGISTER_SUCCESS, memberRegisterResponseDto);
    }
}
