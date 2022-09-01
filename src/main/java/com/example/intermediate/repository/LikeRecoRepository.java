package com.example.intermediate.repository;

import com.example.intermediate.domain.LikeReco;
import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.Recomment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRecoRepository extends JpaRepository<LikeReco, Long> {
    Optional<LikeReco> findLikePostByMemberAndRecomment(Member member, Recomment recomment);
    List<LikeReco> findAllByMember(Member member);
}