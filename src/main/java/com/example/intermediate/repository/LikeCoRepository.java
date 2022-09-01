package com.example.intermediate.repository;

import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.LikeCo;
import com.example.intermediate.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeCoRepository extends JpaRepository<LikeCo, Long> {
    Optional<LikeCo> findLikeCoByMemberAndComment(Member member, Comment comment);
    List<LikeCo> findAllByMember(Member member);
}