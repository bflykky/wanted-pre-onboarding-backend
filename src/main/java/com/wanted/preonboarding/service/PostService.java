package com.wanted.preonboarding.service;

import com.wanted.preonboarding.dto.post.PostCreateResponseDto;
import com.wanted.preonboarding.dto.post.PostCreateRequestDto;
import com.wanted.preonboarding.dto.post.PostFindResponseDto;
import com.wanted.preonboarding.dto.post.PostModifyRequestDto;
import com.wanted.preonboarding.dto.post.PostModifyResponseDto;
import com.wanted.preonboarding.entity.Post;
import com.wanted.preonboarding.error.ErrorCode;
import com.wanted.preonboarding.error.exception.EntityNotFoundException;
import com.wanted.preonboarding.repository.PostRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostCreateResponseDto createPost(PostCreateRequestDto postCreateRequestDto) {
        Post post = new Post(postCreateRequestDto.getTitle(), postCreateRequestDto.getContent());
        Long postId = postRepository.save(post).getId();
        return PostCreateResponseDto.of(postId);
    }

    public PostFindResponseDto findPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));
        return PostFindResponseDto.of(post);
    }

    public List<PostFindResponseDto> findAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostFindResponseDto> responseDtoList = new ArrayList<>();
        for (Post post : posts) {
            responseDtoList.add(PostFindResponseDto.of(post));
        }

        return responseDtoList;
    }
    public List<PostFindResponseDto> findAllPosts(Pageable pageable) {
        List<Post> posts = postRepository.findAll(pageable).toList();
        List<PostFindResponseDto> responseDtoList = new ArrayList<>();
        for (Post post : posts) {
            responseDtoList.add(PostFindResponseDto.of(post));
        }

        return responseDtoList;
    }

    public PostModifyResponseDto modifyPost(Long postId, PostModifyRequestDto postModifyRequestDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));
        post.modifyTitle(postModifyRequestDto.getTitle());
        post.modifyContent(postModifyRequestDto.getContent());

        Long updatedPostId = postRepository.save(post).getId();
        return PostModifyResponseDto.of(updatedPostId);
    }

    public void deletePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new EntityNotFoundException(ErrorCode.POST_NOT_FOUND);
        }

        postRepository.deleteById(postId);
    }

}
