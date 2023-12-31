package com.wanted.preonboarding.service;

import com.wanted.preonboarding.dto.member.MemberInfoResponseDto;
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

import java.util.ArrayList;
import java.util.List;

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

    public List<MemberInfoResponseDto> findAllMembers() {
        List<Member> members = memberRepository.findAll();
        List<MemberInfoResponseDto> memberInfoResponseDtoList = new ArrayList<>();
        for (Member member : members) {
            memberInfoResponseDtoList.add(MemberInfoResponseDto.of(member));
        }

        return memberInfoResponseDtoList;
    }
}
