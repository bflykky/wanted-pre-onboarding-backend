package com.wanted.preonboarding.jwt.provider;

import com.wanted.preonboarding.jwt.token.CustomUsernamePasswordAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component("jwtIssueProvider")
@RequiredArgsConstructor
public class JwtIssueProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = (String)authentication.getCredentials();
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
            }

            // credentials 부분에 null인 이유: 보안 상 비밀번호를 노출하지 않기 위해.
            CustomUsernamePasswordAuthenticationToken authenticationToken =
                    new CustomUsernamePasswordAuthenticationToken(new User(email, password, userDetails.getAuthorities()), null, userDetails.getAuthorities());

            return authenticationToken;
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("해당 이메일을 가진 회원이 존재하지 않습니다.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
