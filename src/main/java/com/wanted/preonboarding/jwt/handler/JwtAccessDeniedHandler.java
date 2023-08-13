package com.wanted.preonboarding.jwt.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 필요한 권한이 존재하지 않는 경우, 403 에러를 발생시키는 함수
     * 특정 게시글 수정/삭제 요청을 한 클라이언트가 해당 게시글의 작성자가 아닌 경우.
     * @param request that resulted in an <code>AccessDeniedException</code>
     * @param response so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     * @throws IOException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        System.out.println("===================특정 게시글 수정/삭제 요청을 한 클라이언트가 해당 게시글의 작성자가 아닌 경우.====================");
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
