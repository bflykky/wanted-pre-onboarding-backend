package com.wanted.preonboarding.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class PostNotFoundException extends AuthenticationException {
    public PostNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public PostNotFoundException(String msg) {
        super(msg);
    }
}
