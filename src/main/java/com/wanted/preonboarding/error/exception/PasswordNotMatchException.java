package com.wanted.preonboarding.error.exception;

import com.wanted.preonboarding.error.ErrorCode;

public class PasswordNotMatchException extends BusinessException {
    public PasswordNotMatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
