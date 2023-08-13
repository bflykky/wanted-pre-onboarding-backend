package com.wanted.preonboarding.jwt.provider;

import com.wanted.preonboarding.entity.Post;
import com.wanted.preonboarding.error.ErrorCode;
import com.wanted.preonboarding.error.exception.EntityNotFoundException;
import com.wanted.preonboarding.jwt.token.JwtAuthenticationToken;
import com.wanted.preonboarding.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component("writerAuthorizationProvider")
@RequiredArgsConstructor
public class WriterAuthorizationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PostRepository postRepository;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        Long postId = ((JwtAuthenticationToken)authentication).getPostId();

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));
        if (!email.equals(post.getWriter())) {
            // 수정 또는 삭제를 요청한 클라이언트가 해당 게시글 작성자가 아닌 경우
            throw new BadCredentialsException("해당 게시글의 작성자가 아닙니다.");
        }

        JwtAuthenticationToken authenticationToken =
                new JwtAuthenticationToken(new User(email, userDetails.getPassword(), userDetails.getAuthorities()), (String)authentication.getCredentials(), postId);

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
