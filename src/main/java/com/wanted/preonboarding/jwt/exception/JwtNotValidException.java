package com.wanted.preonboarding.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtNotValidException extends AuthenticationException {
    public JwtNotValidException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JwtNotValidException(String msg) {
        super(msg);
    }
}
