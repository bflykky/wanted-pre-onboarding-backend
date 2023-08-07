package com.wanted.preonboarding.error.exception;

import com.wanted.preonboarding.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
