package com.example.intermediate.controller.response;

import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.Post;
import com.example.intermediate.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ListResponseProvider {

    private final CommentRepository commentRepository;

    public List<PostListResponseDto> GetPostListResponse(List<Post> postList){
        List<PostListResponseDto> postListResponseDtos = new ArrayList<>();

        if(!postList.isEmpty()){
            for(Post post : postList){
                List<Comment> comment = commentRepository.findAllByPost(post);
                postListResponseDtos.add(
                        PostListResponseDto.builder()
                                .id(post.getId())
                                .title(post.getTitle())
                                .author(post.getMember().getNickname())
                                .likesNum(post.getLikesNum())
                                .commentsNum(comment.size())
                                .createdAt(post.getCreatedAt())
                                .modifiedAt(post.getModifiedAt())
                                .build()
                );
            }
        }
        return postListResponseDtos;
    }
}
