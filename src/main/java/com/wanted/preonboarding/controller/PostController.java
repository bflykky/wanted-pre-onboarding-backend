package com.wanted.preonboarding.controller;

import com.wanted.preonboarding.dto.post.PostCreateRequestDto;
import com.wanted.preonboarding.dto.post.PostCreateResponseDto;
import com.wanted.preonboarding.dto.post.PostFindResponseDto;
import com.wanted.preonboarding.dto.post.PostModifyRequestDto;
import com.wanted.preonboarding.dto.post.PostModifyResponseDto;
import com.wanted.preonboarding.jwt.provider.JwtProvider;
import com.wanted.preonboarding.result.ResultCode;
import com.wanted.preonboarding.result.ResultResponse;
import com.wanted.preonboarding.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final JwtProvider jwtProvider;

    @PostMapping(value = "/posts")
    public ResultResponse createPost(@RequestHeader("Authorization") String jwt, @RequestBody PostCreateRequestDto postCreateRequestDto) {
        System.out.println("Authorization: " + jwt);

        PostCreateResponseDto postCreateResponseDto = postService.createPost(postCreateRequestDto, jwtProvider.getSubject(jwt));
        return ResultResponse.of(ResultCode.POST_CREATE_SUCCESS, postCreateResponseDto);
    }

    @GetMapping(value = "/posts/{postId}")
    public ResultResponse readPost(@PathVariable("postId") Long postId) {
        PostFindResponseDto postFindResponseDto = postService.findPost(postId);
        return ResultResponse.of(ResultCode.POST_FIND_SUCCESS, postFindResponseDto);
    }

    @GetMapping(value = "/posts")
    public ResultResponse readAllPosts(@PageableDefault Pageable pageable) {
        List<PostFindResponseDto> postFindResponseDtoList = postService.findAllPosts(pageable);
        return ResultResponse.of(ResultCode.ALL_POSTS_FIND_SUCCESS, postFindResponseDtoList);
    }

    // 게시글 수정 권한은 작성자에게만 있으므로, 요청자가 작성자인지 확인하는 절차 필요.
    @PatchMapping(value = "/posts/{postId}")
    public ResultResponse modifyPost(@PathVariable("postId") Long postId, @RequestBody PostModifyRequestDto postModifyRequestDto) {
        PostModifyResponseDto postModifyResponseDto = postService.modifyPost(postId, postModifyRequestDto);
        return ResultResponse.of(ResultCode.POST_MODIFY_SUCCESS, postModifyResponseDto);
    }

    // 게시글 수정 권한은 작성자에게만 있으므로, 요청자가 작성자인지 확인하는 절차 필요.
    @DeleteMapping(value = "/posts/{postId}")
    public ResultResponse deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
        return ResultResponse.of(ResultCode.POST_DELETE_SUCCESS);
    }

}
