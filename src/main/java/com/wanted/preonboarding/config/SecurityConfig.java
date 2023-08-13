package com.wanted.preonboarding.config;

import com.wanted.preonboarding.jwt.filter.CustomBearerTokenAuthenticationFilter;
import com.wanted.preonboarding.jwt.filter.WriterAuthorizationFilter;
import com.wanted.preonboarding.jwt.handler.JwtAccessDeniedHandler;
import com.wanted.preonboarding.jwt.handler.JwtAuthenticationEntryPoint;
import com.wanted.preonboarding.jwt.filter.JwtAuthorizationFilter;
import com.wanted.preonboarding.jwt.provider.CustomBearerTokenAuthenticationProvider;
import com.wanted.preonboarding.jwt.provider.JwtProvider;
import com.wanted.preonboarding.jwt.provider.JwtAuthorizationProvider;
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


@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtProvider jwtProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthorizationProvider jwtAuthorizationProvider;
    private final CustomBearerTokenAuthenticationProvider customBearerTokenAuthenticationProvider;
    private final WriterAuthorizationProvider writerAuthorizationProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final Validator validator;

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(AuthenticationManager authenticationManager) throws Exception {
        final JwtAuthorizationFilter filter = new JwtAuthorizationFilter(validator, jwtProvider);
        filter.setAuthenticationManager(authenticationManager);

        return filter;
    }

    @Bean
    public CustomBearerTokenAuthenticationFilter customBearerTokenAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        final CustomBearerTokenAuthenticationFilter filter = new CustomBearerTokenAuthenticationFilter(jwtProvider);
        filter.setAuthenticationManager(authenticationManager);

        return filter;
    }

    @Bean
    public WriterAuthorizationFilter writerAuthorizationFilter(AuthenticationManager authenticationManager) throws Exception {
        final WriterAuthorizationFilter filter = new WriterAuthorizationFilter(validator, jwtProvider);
        filter.setAuthenticationManager(authenticationManager);

        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .authenticationProvider(jwtAuthorizationProvider)
                .authenticationProvider(customBearerTokenAuthenticationProvider)
                .authenticationProvider(writerAuthorizationProvider)
                .build();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
//                .csrf(csrf -> csrf.disable()) // token을 사용하는 방식이므로, csrf disable
                .csrf().disable()

                .formLogin()
                    .loginPage("/members/login")
                    .loginProcessingUrl("/members/login")
                    .permitAll()
                    .and()
                    .csrf().disable()

                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )

                .authorizeHttpRequests()
                .antMatchers("/members", "/members/login").permitAll()
                .antMatchers(HttpMethod.GET, "/posts").permitAll()
                .antMatchers(HttpMethod.GET, "/posts/**").permitAll()
                .and()

                // 이유는 못 찾았는데, requestMatchers 파라미터로 String 인식이 안됨. 그래서 antMatchers로 대체.
//                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
//                        .requestMatchers("/members", "/members/login").permitAll()
//                        .anyRequest().authenticated()
//                )

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .addFilterBefore(jwtAuthorizationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customBearerTokenAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(writerAuthorizationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}

