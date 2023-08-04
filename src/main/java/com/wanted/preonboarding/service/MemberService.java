package com.wanted.preonboarding.service;

import com.wanted.preonboarding.dto.MemberRegisterResponseDto;
import com.wanted.preonboarding.entity.Member;
import com.wanted.preonboarding.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public MemberRegisterResponseDto register(String email, String password) {
        // password 암호화 함수 적용.
        String hashPassword = password;
        Long memberId = memberRepository.save(new Member(email, hashPassword)).getId();

        return MemberRegisterResponseDto.of(memberId);
    }
}
