package com.wanted.preonboarding.result;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultCode {
    MEMBER_REGISTER_SUCCESS("M001", "회원가입이 성공적으로 완료되었습니다."),
    ;

    private final String code;
    private final String message;
}
