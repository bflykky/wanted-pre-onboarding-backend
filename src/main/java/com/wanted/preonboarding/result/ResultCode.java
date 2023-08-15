package com.wanted.preonboarding.result;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultCode {
    MEMBER_JOIN_SUCCESS("M001", "회원가입이 성공적으로 완료되었습니다."),
    MEMBER_LOGIN_SUCCESS("M002", "로그인이 성공적으로 완료되었습니다."),
    ALL_MEMBERS_FIND_SUCCESS("M003", "모든 사용자들을 성공적으로 조회하였습니다."),
    POST_CREATE_SUCCESS("M004", "게시글을 성공적으로 등록하였습니다." ),
    POST_FIND_SUCCESS("M005", "입력한 postId의 게시글을 성공적으로 조회하였습니다."),
    ALL_POSTS_FIND_SUCCESS("M006", "모든 게시글을 성공적으로 조회하였습니다."),
    POST_MODIFY_SUCCESS("M007", "입력한 postId의 게시글을 성공적으로 수정하였습니다."),

    POST_DELETE_SUCCESS("M008", "입력한 postId의 게시글을 성공적으로 삭제하였습니다"),

    ;

    private final String code;
    private final String message;
}
