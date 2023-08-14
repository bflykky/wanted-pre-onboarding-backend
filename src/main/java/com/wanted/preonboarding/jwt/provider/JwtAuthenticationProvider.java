package com.wanted.preonboarding.jwt.provider;

import com.wanted.preonboarding.jwt.exception.JwtNotValidException;
import com.wanted.preonboarding.jwt.token.WriterAuthenticationToken;
import com.wanted.preonboarding.service.CustomUserDetailsService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("jwtAuthenticationProvider")
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtProvider jwtProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            String jwt = String.valueOf(authentication.getCredentials());
            // 토큰이 유효한지 검증하는 코드
            jwtProvider.validateToken(jwt);
            // 토큰의 subject가 데이터베이스에 존재하는지 검증하는 코드
            String email = authentication.getName();
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

            WriterAuthenticationToken authenticationToken = new WriterAuthenticationToken(
                    new User(email, userDetails.getPassword(), userDetails.getAuthorities()),
                    authentication.getCredentials(),
                    userDetails.getAuthorities());
            return authenticationToken;
        } catch (JwtException e) {
            throw new JwtNotValidException(e.getMessage());
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("해당 이메일을 가진 회원이 존재하지 않습니다.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return WriterAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
