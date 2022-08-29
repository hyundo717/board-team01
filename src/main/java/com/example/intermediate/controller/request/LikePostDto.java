package com.example.intermediate.controller.request;

import com.example.intermediate.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikePostDto {
    private Member member;
    private Long postId;
}
