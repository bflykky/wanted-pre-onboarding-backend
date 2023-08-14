package com.wanted.preonboarding.jwt.handler;

import com.wanted.preonboarding.error.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 발생할 수 있는 에러
// 1. Authorization 헤더가 없는 경우 JwtNotFoundException("JWT가 존재하지 않습니다. 로그인을 통해 JWT를 발급받아야 합니다.");
// 2. Authorization 헤더의 타입이 'Bearer'가 아닌 경우 BearerTokenNotValidException("인증 타입이 \'Bearer\'가 아닙니다.");
// 3. 토큰이 유효하지 않은 경우(만료 등등..) BearerTokenNotValidException
// 4. 토큰의 subject가 DB에 없는 경우 BadCredentialsException("해당 이메일을 가진 회원이 존재하지 않습니다.");
@Component
public class JwtAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HandlerUtility.writeResponse(request, response, ErrorCode.JWT_AUTHENTICATION_FAILED, exception.getMessage());
    }
}
