package com.wanted.preonboarding.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtNotFoundException extends AuthenticationException {
    public JwtNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JwtNotFoundException(String msg) {
        super(msg);
    }
}
