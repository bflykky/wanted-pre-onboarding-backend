package com.wanted.preonboarding.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.dto.member.MemberLoginRequestDto;
import com.wanted.preonboarding.dto.member.MemberLoginResponseDto;
import com.wanted.preonboarding.jwt.provider.JwtProvider;
import com.wanted.preonboarding.jwt.token.CustomUsernamePasswordAuthenticationToken;
import com.wanted.preonboarding.result.ResultCode;
import com.wanted.preonboarding.result.ResultResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * 클라이언트의 이메일, 비밀번호를 통해 가입된 회원임을 인증 후, JWT를 발급하는 필터
 */
public class JwtAuthorizationFilter extends AbstractAuthenticationProcessingFilter {
    private static final AntPathRequestMatcher ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/members/login", "POST");
    private final Validator validator;
    final ObjectMapper objectMapper = new ObjectMapper();
    private final JwtProvider jwtProvider;

    public JwtAuthorizationFilter(Validator validator, JwtProvider jwtProvider) {
        super(ANT_PATH_REQUEST_MATCHER);
        this.validator = validator;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            try {
                final String requestBody = IOUtils.toString(request.getReader());
                final MemberLoginRequestDto memberLoginRequestDto = objectMapper.readValue(requestBody, MemberLoginRequestDto.class);
                Set<ConstraintViolation<MemberLoginRequestDto>> violations = validator.validate(memberLoginRequestDto);

                final CustomUsernamePasswordAuthenticationToken authenticationToken =
                        new CustomUsernamePasswordAuthenticationToken(memberLoginRequestDto.getEmail(), memberLoginRequestDto.getPassword());

                return super.getAuthenticationManager().authenticate(authenticationToken);
            } catch (IOException e) {
                throw new AuthenticationServiceException("Authentication failed while converting request body.");
            }
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String jwt = "Bearer " + jwtProvider.createToken(authResult);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try (OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, ResultResponse.of(ResultCode.MEMBER_LOGIN_SUCCESS, MemberLoginResponseDto.of(jwt)));
            os.flush();
        }

         super.successfulAuthentication(request, response, chain, authResult);
    }
}