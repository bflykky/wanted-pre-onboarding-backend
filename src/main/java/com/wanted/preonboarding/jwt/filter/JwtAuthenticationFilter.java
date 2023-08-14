package com.wanted.preonboarding.jwt.filter;

import com.wanted.preonboarding.jwt.exception.JwtNotValidException;
import com.wanted.preonboarding.jwt.exception.JwtNotFoundException;
import com.wanted.preonboarding.jwt.provider.JwtProvider;
import com.wanted.preonboarding.jwt.token.WriterAuthenticationToken;
import io.jsonwebtoken.JwtException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Request 헤더의 JWT 토큰의 유무 및 유효성을 확인하여, 로그인된 사용자인지 검증하는 필터.
 * 1. JWT 헤더에 포함 유무 확인(attemptAuthentication()에서)
 * 2. 존재하는 사용자인지 확인(provider의 authenticate()에서)
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final OrRequestMatcher REQUEST_MATCHER = new OrRequestMatcher(
            new AntPathRequestMatcher("/posts", "POST"));

    private final JwtProvider jwtProvider;
    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        super(REQUEST_MATCHER);
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String jwt = request.getHeader("Authorization");
        if (jwt == null || jwt.equals("")) {
            throw new JwtNotFoundException("JWT가 존재하지 않습니다. 로그인을 통해 JWT를 발급받아야 합니다.");
        } else if (jwt.startsWith("Bearer ") == false) {
            throw new JwtNotValidException("인증 타입이 \'Bearer\'가 아닙니다.");
        }
        String credentials = jwt.substring("Bearer ".length());
        try {
            String email = jwtProvider.getAuthentication(credentials).getName();

            WriterAuthenticationToken authenticationToken = new WriterAuthenticationToken(email, credentials);
            return super.getAuthenticationManager().authenticate(authenticationToken);
        } catch (JwtException e) {
            throw new JwtNotValidException(e.getMessage());
        }
    }
}
