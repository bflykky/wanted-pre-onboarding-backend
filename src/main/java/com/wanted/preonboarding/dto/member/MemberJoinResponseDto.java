package com.wanted.preonboarding.dto.member;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberJoinResponseDto {
    private Long memberId;

    public static MemberJoinResponseDto of(Long memberId) {
        return MemberJoinResponseDto.builder()
                .memberId(memberId)
                .build();
    }
}
