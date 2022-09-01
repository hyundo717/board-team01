package com.example.intermediate.repository;

import com.example.intermediate.domain.LikeReco;
import com.example.intermediate.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRecoRepository extends JpaRepository<LikeReco, Long> {
    Optional<LikeReco> findLikePostByMemberAndRecommentId(Member member, Long recommentId);
    List<LikeReco> findAllByMember(Member member);
}