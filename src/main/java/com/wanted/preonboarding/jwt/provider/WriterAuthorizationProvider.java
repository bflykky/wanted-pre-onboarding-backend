package com.wanted.preonboarding.jwt.provider;

import com.wanted.preonboarding.entity.Post;
import com.wanted.preonboarding.error.ErrorCode;
import com.wanted.preonboarding.error.exception.EntityNotFoundException;
import com.wanted.preonboarding.jwt.exception.JwtNotValidException;
import com.wanted.preonboarding.jwt.exception.PostNotFoundException;
import com.wanted.preonboarding.jwt.exception.WriterAuthorizationException;
import com.wanted.preonboarding.jwt.token.JwtAuthenticationToken;
import com.wanted.preonboarding.repository.PostRepository;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("writerAuthorizationProvider")
@RequiredArgsConstructor
public class WriterAuthorizationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PostRepository postRepository;
    private final JwtProvider jwtProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            String jwt = String.valueOf(authentication.getCredentials());
            // 토큰이 유효한지 검증하는 코드
            jwtProvider.validateToken(jwt);

            String email = authentication.getName();
            Long postId = ((JwtAuthenticationToken) authentication).getPostId();
            Post post = postRepository.findById(postId)
                            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if (!email.equals(post.getWriter())) {
                // 수정 또는 삭제를 요청한 클라이언트가 해당 게시글 작성자가 아닌 경우
                throw new WriterAuthorizationException("작성자만 수정/삭제가 가능합니다.");
            }

            JwtAuthenticationToken authenticationToken =
                    new JwtAuthenticationToken(new User(email, userDetails.getPassword(), userDetails.getAuthorities()), (String) authentication.getCredentials(), postId);
            return authenticationToken;
        } catch (JwtException e) {
            throw new JwtNotValidException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new PostNotFoundException(e.getMessage());
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("해당 이메일을 가진 회원이 존재하지 않습니다.");
        }
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
