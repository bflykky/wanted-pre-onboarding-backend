package com.wanted.preonboarding.dto.post;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostCreateResponseDto {
    private Long postId;

    public static PostCreateResponseDto of(Long postId) {
        return PostCreateResponseDto.builder()
                .postId(postId)
                .build();
    }
}
