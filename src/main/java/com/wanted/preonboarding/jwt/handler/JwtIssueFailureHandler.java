package com.wanted.preonboarding.jwt.handler;

import com.wanted.preonboarding.error.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 발생할 수 있는 예외
// 1. 이메일에 '@'가 포함되어 있지 않음(CustomMethodArgumentNotValidException)
// 2. 비밀번호가 8자 이상이 아님(CustomMethodArgumentNotValidException)
// 3. 해당 이메일 DB에서 찾을 수 없음(CustomMethodArgumentNotValidException)
// 4. 비밀번호가 일치하지 않음(BadCredentialException)
@Component
public class JwtIssueFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HandlerUtility.writeResponse(request, response, ErrorCode.AUTHENTICATION_FAILED, exception.getMessage());
    }
}
