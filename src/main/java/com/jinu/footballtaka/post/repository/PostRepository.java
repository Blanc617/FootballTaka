package com.jinu.footballtaka.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jinu.footballtaka.post.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUserIdOrderByIdDesc(Integer userId);
    List<Post> findByUserIdAndBoardTypeOrderByIdDesc(Integer userId, String boardType);
}
