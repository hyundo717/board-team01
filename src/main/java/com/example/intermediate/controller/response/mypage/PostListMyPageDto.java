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
public class PostListMyPageDto {
    private Long postId;
    private String title;
    private String author;
    private String imgUrl;
    private String content;
    private int likesNum;
    private int commentsNum;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
