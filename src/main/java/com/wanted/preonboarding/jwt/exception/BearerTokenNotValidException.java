package com.wanted.preonboarding.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class BearerTokenNotValidException extends AuthenticationException {
    public BearerTokenNotValidException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BearerTokenNotValidException(String msg) {
        super(msg);
    }
}
