package com.wanted.preonboarding.jwt.handler;

import com.wanted.preonboarding.error.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 발생할 수 있는 예외
 * 1. Authorization 헤더가 없는 경우 JwtNotFoundException("JWT가 존재하지 않습니다. 로그인을 통해 JWT를 발급받아야 합니다.");
 * 2. Authorization 헤더의 타입이 'Bearer'가 아닌 경우 BearerTokenNotValidException("인증 타입이 \'Bearer\'가 아닙니다.");
 * 4. 요청한 게시글이 존재하지 않는 경우 EntityNotFoundException
 * 3. 토큰이 유효하지 않은 경우(만료 등등..) BearerTokenNotValidException
 * 4. 토큰의 subject가 DB에 없는 경우 BadCredentialsException("해당 이메일을 가진 회원이 존재하지 않습니다.");
 * 5. 요청 클라이언트의 토큰 subject가 해당 게시글 작성자와 일치하지 않는 경우 BadCredentialsException("해당 게시글의 작성자가 아닙니다.");
 *
 */
@Component
public class WriterAuthorizationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (request.getMethod().equals("PATCH")) {
            onAuthenticationFailureToModifyPost(request, response, exception);
        } else if (request.getMethod().equals("DELETE")) {
            onAuthenticationFailureToDeletePost(request, response, exception);
        }
    }

    private void onAuthenticationFailureToModifyPost(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        if (exception.getMessage().equals("작성자만 수정/삭제가 가능합니다.")) {
            HandlerUtility.writeResponse(request, response, ErrorCode.NO_AUTHORIZED_TO_MODIFY_POST);
        } else {
            HandlerUtility.writeResponse(request, response, HttpStatus.BAD_REQUEST, "E010", exception.getMessage());
        }
    }

    private void onAuthenticationFailureToDeletePost(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        if (exception.getMessage().equals("작성자만 수정/삭제가 가능합니다.")) {
            HandlerUtility.writeResponse(request, response, ErrorCode.NO_AUTHORIZED_TO_DELETE_POST);
        } else {
            HandlerUtility.writeResponse(request, response, HttpStatus.BAD_REQUEST, "E010", exception.getMessage());
        }
    }
}
