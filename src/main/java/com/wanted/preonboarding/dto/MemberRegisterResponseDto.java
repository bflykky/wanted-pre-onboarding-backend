package com.wanted.preonboarding.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberRegisterResponseDto {
    private Long memberId;

    public static MemberRegisterResponseDto of(Long memberId) {
        return MemberRegisterResponseDto.builder()
                .memberId(memberId)
                .build();
    }
}
