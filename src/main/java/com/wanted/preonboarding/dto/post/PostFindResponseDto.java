package com.wanted.preonboarding.dto.post;

import com.wanted.preonboarding.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class PostFindResponseDto {
    private Long postId;
    private String writer;
    private LocalDateTime writeDate;

    private String title;
    private String content;

    public static PostFindResponseDto of(Post post) {
        return PostFindResponseDto.builder()
                .postId(post.getId())
                .writer(post.getWriter())
                .writeDate(post.getWriteDate())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
