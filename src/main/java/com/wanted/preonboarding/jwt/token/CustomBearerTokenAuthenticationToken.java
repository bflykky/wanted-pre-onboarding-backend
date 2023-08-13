package com.wanted.preonboarding.jwt.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomBearerTokenAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public CustomBearerTokenAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public CustomBearerTokenAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
