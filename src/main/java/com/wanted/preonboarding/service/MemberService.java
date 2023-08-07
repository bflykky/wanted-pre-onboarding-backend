package com.wanted.preonboarding.service;

import com.wanted.preonboarding.dto.member.MemberJoinResponseDto;
import com.wanted.preonboarding.dto.member.MemberLoginResponseDto;
import com.wanted.preonboarding.entity.Member;
import com.wanted.preonboarding.error.ErrorCode;
import com.wanted.preonboarding.error.exception.EntityNotFoundException;
import com.wanted.preonboarding.error.exception.PasswordNotMatchException;
import com.wanted.preonboarding.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    public MemberJoinResponseDto join(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Long memberId = memberRepository.save(new Member(email, encodedPassword)).getId();

        return MemberJoinResponseDto.of(memberId);
    }

    public MemberLoginResponseDto login(String email, String password) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        if (passwordEncoder.matches(password, member.getPassword())) {
            // JWT 형식의 Access Token 발급
            String token = null;
            return MemberLoginResponseDto.of(token);
        } else {
            throw new PasswordNotMatchException(ErrorCode.PASSWORD_NOT_MATCH);
        }
    }
}
