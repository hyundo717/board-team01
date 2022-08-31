package com.example.intermediate.controller.response.mypage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentListMyPageDto {
    private Long commentId;
    private Long postId;
    private String author;
    private String content;
    private int likesNum;
    private int recommentsNum;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
