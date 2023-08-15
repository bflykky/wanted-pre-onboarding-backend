package com.wanted.preonboarding.config;

import com.wanted.preonboarding.jwt.filter.JwtAuthenticationFilter;
import com.wanted.preonboarding.jwt.filter.WriterAuthorizationFilter;
import com.wanted.preonboarding.jwt.handler.CustomAuthenticationSuccessHandler;
import com.wanted.preonboarding.jwt.handler.JwtAccessDeniedHandler;
import com.wanted.preonboarding.jwt.handler.JwtAuthenticationEntryPoint;
import com.wanted.preonboarding.jwt.filter.JwtIssueFilter;
import com.wanted.preonboarding.jwt.handler.JwtAuthenticationFailureHandler;
import com.wanted.preonboarding.jwt.handler.JwtIssueFailureHandler;
import com.wanted.preonboarding.jwt.handler.WriterAuthorizationFailureHandler;
import com.wanted.preonboarding.jwt.provider.JwtAuthenticationProvider;
import com.wanted.preonboarding.jwt.provider.JwtProvider;
import com.wanted.preonboarding.jwt.provider.JwtIssueProvider;
import com.wanted.preonboarding.jwt.provider.WriterAuthorizationProvider;
import com.wanted.preonboarding.service.CustomUserDetailsService;
import javax.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtProvider jwtProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtIssueFailureHandler jwtIssueFailureHandler;
    private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private final WriterAuthorizationFailureHandler writerAuthorizationFailureHandler;

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    private final JwtIssueProvider jwtIssueProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final WriterAuthorizationProvider writerAuthorizationProvider;
    private final Validator validator;


    @Bean
    public JwtIssueFilter jwtIssueFilter(AuthenticationManager authenticationManager) throws Exception {
        final JwtIssueFilter filter = new JwtIssueFilter(validator, jwtProvider);
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationFailureHandler(jwtIssueFailureHandler);

        return filter;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        final JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtProvider);
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(jwtAuthenticationFailureHandler);

        return filter;
    }

    @Bean
    public WriterAuthorizationFilter writerAuthorizationFilter(AuthenticationManager authenticationManager) throws Exception {
        final WriterAuthorizationFilter filter = new WriterAuthorizationFilter(jwtProvider);
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(writerAuthorizationFailureHandler);

        return filter;
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .parentAuthenticationManager(null)
//                .userDetailsService(customUserDetailsService)
//                .passwordEncoder(passwordEncoder)
//                .and()
                .authenticationProvider(jwtIssueProvider)
                .authenticationProvider(jwtAuthenticationProvider)
                .authenticationProvider(writerAuthorizationProvider)
                .build();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // token을 사용하기에 csrf disable
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )

                .authorizeHttpRequests()
                .antMatchers("/members", "/members/login").permitAll()
                .antMatchers(HttpMethod.GET, "/posts").permitAll()
                .antMatchers(HttpMethod.GET, "/posts/**").permitAll()
                .and()

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )


                .addFilterBefore(jwtIssueFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(writerAuthorizationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}

