package com.wanted.preonboarding.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Long id;

    @NotNull
    @Column(name = "writer")
    private String writer;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    public Post(Member member, LocalDateTime writeDate, String title, String content) {
        this.writer = member.getEmail();
        this.writeDate = writeDate;
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public void modifyTitle(String title) {
        this.title = title;
    }
    public void modifyContent(String content) {
        this.content = content;
    }
}
