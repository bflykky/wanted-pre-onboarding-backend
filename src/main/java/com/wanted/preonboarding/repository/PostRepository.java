package com.wanted.preonboarding.repository;

import com.wanted.preonboarding.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
