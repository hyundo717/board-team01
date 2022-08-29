package com.example.intermediate.repository;

import com.example.intermediate.domain.LikePost;
import com.example.intermediate.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    Optional<LikePost> findLikePostByMemberAndPostId(Member member, Long postId);
}
