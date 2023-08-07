package com.wanted.preonboarding.dto.member;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberLoginResponseDto {
    private String token;

    public static MemberLoginResponseDto of(String token) {
        return MemberLoginResponseDto.builder()
                .token(token)
                .build();
    }
}
