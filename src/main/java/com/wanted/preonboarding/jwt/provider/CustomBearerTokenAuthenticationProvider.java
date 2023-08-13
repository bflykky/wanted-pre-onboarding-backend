package com.wanted.preonboarding.jwt.provider;

import com.wanted.preonboarding.jwt.exception.BearerTokenNotValidException;
import com.wanted.preonboarding.jwt.token.CustomBearerTokenAuthenticationToken;
import com.wanted.preonboarding.jwt.token.CustomUsernamePasswordAuthenticationToken;
import com.wanted.preonboarding.service.CustomUserDetailsService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("customBearerTokenAuthenticationProvider")
@RequiredArgsConstructor
public class CustomBearerTokenAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtProvider jwtProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String jwt = String.valueOf(authentication.getCredentials());

        // 토큰이 유효한지 검증하는 코드
        try {
            jwtProvider.validateToken(jwt);
        } catch (JwtException e) {
            throw new BearerTokenNotValidException(e.getMessage());
        }

        // 토큰의 subject가 데이터베이스에 존재하는지 검증하는 코드
        String email = authentication.getName();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        CustomBearerTokenAuthenticationToken authenticationToken = new CustomBearerTokenAuthenticationToken(
                new User(email, userDetails.getPassword(), userDetails.getAuthorities()),
                authentication.getCredentials(),
                userDetails.getAuthorities());

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomBearerTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
