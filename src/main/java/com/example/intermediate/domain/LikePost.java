package com.example.intermediate.domain;

import com.example.intermediate.controller.request.LikePostDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LikePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private Long postId;

    public void update(LikePostDto likePostDto) {
        this.member = likePostDto.getMember();
        this.postId = likePostDto.getPostId();
    }
}
