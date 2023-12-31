package com.wanted.preonboarding.jwt.filter;

import com.wanted.preonboarding.jwt.exception.JwtNotValidException;
import com.wanted.preonboarding.jwt.exception.JwtNotFoundException;
import com.wanted.preonboarding.jwt.provider.JwtProvider;
import com.wanted.preonboarding.jwt.token.JwtAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.Validator;
import java.io.IOException;


/**
 * 클라이언트 헤더의 Jwt를 분석해, 수정/삭제하려는 게시글의 작성자인지 검증하는 필터
 */
public class WriterAuthorizationFilter extends AbstractAuthenticationProcessingFilter {
    private static final OrRequestMatcher REQUEST_MATCHER = new OrRequestMatcher(
            new AntPathRequestMatcher("/posts/*", "PATCH"),
            new AntPathRequestMatcher("/posts/*", "DELETE"));

    private final JwtProvider jwtProvider;

    public WriterAuthorizationFilter(JwtProvider jwtProvider) {
        super(REQUEST_MATCHER);
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!(request.getMethod().equals("PATCH") || request.getMethod().equals("DELETE"))) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String jwt = request.getHeader("Authorization");
            if (jwt == null || jwt.equals("")) {
                throw new JwtNotFoundException("JWT가 존재하지 않습니다. 로그인을 통해 JWT를 발급받아야 합니다.");
            } else if (jwt.startsWith("Bearer ") == false) {
                throw new JwtNotValidException("인증 타입이 \'Bearer\'가 아닙니다.");
            }

            String uri = request.getRequestURI();
            Long postId = Long.parseLong(uri.split("/")[2]);
            System.out.println("postId: " + postId);
            Authentication tmpToken = jwtProvider.getAuthentication(jwt.substring("Bearer ".length()));

            final JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(tmpToken, postId);
            return super.getAuthenticationManager().authenticate(authenticationToken);
        }
    }
}
