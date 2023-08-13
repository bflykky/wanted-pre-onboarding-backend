package com.wanted.preonboarding.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.jwt.exception.BearerTokenNotValidException;
import com.wanted.preonboarding.jwt.exception.JwtNotFoundException;
import com.wanted.preonboarding.error.ErrorCode;
import com.wanted.preonboarding.error.ErrorResponse;
import com.wanted.preonboarding.jwt.provider.JwtProvider;
import com.wanted.preonboarding.jwt.token.CustomBearerTokenAuthenticationToken;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Request 헤더의 JWT 토큰의 유무 및 유효성을 확인하여, 로그인된 사용자인지 검증하는 필터.
 */
public class CustomBearerTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final OrRequestMatcher REQUEST_MATCHER = new OrRequestMatcher(
            new AntPathRequestMatcher("/posts", "POST"),
            new AntPathRequestMatcher("/posts/*", "PATCH"),
            new AntPathRequestMatcher("/posts/*", "DELETE"));

    private final JwtProvider jwtProvider;
    public CustomBearerTokenAuthenticationFilter(JwtProvider jwtProvider) {
        super(REQUEST_MATCHER);
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // JWT를 통해 로그인된 사용자인지 확인하는 필터.
        // 1. JWT 헤더에 포함 유무 확인(attemptAuthentication()에서)
        // 2. 존재하는 사용자인지 확인(provider의 authenticate()에서)
        String jwt = request.getHeader("Authorization");
        if (jwt == null) {
            throw new JwtNotFoundException("JWT가 존재하지 않습니다. 로그인을 통해 JWT를 발급받아야 합니다.");
        } else if (jwt.startsWith("Bearer ") == false) {
            throw new BearerTokenNotValidException("인증 타입이 \'Bearer\'가 아닙니다.");
        }
        String credentials = jwt.substring("Bearer ".length());
        String email = jwtProvider.getAuthentication(credentials).getName();

        CustomBearerTokenAuthenticationToken authenticationToken = new CustomBearerTokenAuthenticationToken(email, credentials);
        return super.getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try (OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.AUTHENTICATION_FAILED, failed.getMessage());
            response.setStatus(errorResponse.getStatus());
            objectMapper.writeValue(os, errorResponse);
            os.flush();
        }
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
