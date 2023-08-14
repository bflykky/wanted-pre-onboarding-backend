package com.wanted.preonboarding.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class WriterAuthorizationException extends AuthenticationException {
    public WriterAuthorizationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public WriterAuthorizationException(String msg) {
        super(msg);
    }
}
