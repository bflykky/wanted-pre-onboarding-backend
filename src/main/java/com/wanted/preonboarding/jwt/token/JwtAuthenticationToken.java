package com.wanted.preonboarding.jwt.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final Long postId;
    public JwtAuthenticationToken(Object principal, String jwt, Long postId) {
        super(principal, jwt);
        this.postId = postId;
    }

    public JwtAuthenticationToken(Object principal, String jwt, Collection<? extends GrantedAuthority> authorities, Long postId) {
        super(principal, jwt, authorities);
        this.postId = postId;
    }

    public JwtAuthenticationToken(Authentication authentication, Long postId) {
        super(authentication.getPrincipal(), authentication.getCredentials());
        this.postId = postId;
    }

//    public static JwtAuthenticationToken of(String token, String jwt){
//        return new JwtAuthenticationToken(token, jwt);
//    }

    public Long getPostId() {
        return this.postId;
    }
}
