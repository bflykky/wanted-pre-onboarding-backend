package com.wanted.preonboarding.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @NotNull
    @Column(name = "writer_name")
    private String writerName;

    @NotNull
    @Column(name = "write_date")
    private LocalDateTime writeDate;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @ManyToOne @NotNull
    @JoinColumn(name = "member_id")
    private Member member;
}
