package com.wanted.preonboarding.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    MEMBER_NOT_FOUND(404, "E001", "해당 회원을 찾지 못하였습니다."),
    POST_NOT_FOUND(404, "E002", "해당 게시글이 존재하지 않습니다."),
    PASSWORD_NOT_MATCH(401, "E003", "비밀번호가 일치하지 않습니다."),
    NO_AUTHORIZED_TO_MODIFY_POST(402, "E004", "해당 게시글을 수정할 권한이 없습니다."),
    NO_AUTHORIZED_TO_DELETE_POST(402, "E005", "해당 게시글을 삭제할 권한이 없습니다."),

    ;

    private final int status;

    private final String code;

    private final String message;
}
