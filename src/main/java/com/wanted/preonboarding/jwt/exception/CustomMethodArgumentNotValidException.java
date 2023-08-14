package com.wanted.preonboarding.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class CustomMethodArgumentNotValidException extends AuthenticationException {
    public CustomMethodArgumentNotValidException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CustomMethodArgumentNotValidException(String msg) {
        super(msg);
    }
}
