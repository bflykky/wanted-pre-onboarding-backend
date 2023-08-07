package com.wanted.preonboarding.dto.post;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostModifyResponseDto {
    private Long postId;

    public static PostModifyResponseDto of(Long postId) {
        return PostModifyResponseDto.builder()
                .postId(postId)
                .build();
    }
}

