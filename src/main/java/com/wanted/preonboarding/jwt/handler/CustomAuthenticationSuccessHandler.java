package com.wanted.preonboarding.jwt.handler;

import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String targetUrl = request.getServletPath();

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(targetUrl);
        requestDispatcher.forward(request, response);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        setDefaultTargetUrl(request.getRequestURI());
        if (!request.getMethod().equals(HttpMethod.GET)) {
            this.handle(request, response, authentication);
        }
        super.clearAuthenticationAttributes(request);
    }
}
