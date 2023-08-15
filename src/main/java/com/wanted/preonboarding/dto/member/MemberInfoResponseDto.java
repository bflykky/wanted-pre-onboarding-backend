package com.wanted.preonboarding.dto.member;

import com.wanted.preonboarding.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberInfoResponseDto {
    private Long memberId;
    private String email;

    public static MemberInfoResponseDto of(Member member) {
        return MemberInfoResponseDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .build();
    }
}
