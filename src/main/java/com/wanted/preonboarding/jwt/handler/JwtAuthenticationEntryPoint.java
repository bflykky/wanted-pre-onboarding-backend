package com.wanted.preonboarding.jwt.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * 자격증명을 제공하지 않고 접근하려 할 때, 401 에러를 발생시키는 함수
     * 로그인을 하지 않은 사용자(토큰을 헤더에 갖고 있지 않은 클라이언트)가 게시글 작성/수정/삭제를 하려는 경우.
     * @param request that resulted in an <code>AuthenticationException</code>
     * @param response so that the user agent can begin authentication
     * @param authenticationException that caused the invocation
     * @throws IOException
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authenticationException) throws IOException {
        System.out.println("====================로그인을 하지 않은 사용자(토큰을 헤더에 갖고 있지 않은 클라이언트)가 게시글 작성/수정/삭제를 하려는 경우.======================");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
