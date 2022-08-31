package com.example.intermediate.controller.response;

import com.example.intermediate.controller.response.mypage.PostListMyPageDto;
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
                                .imgUrl(post.getImgUrl())
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

    public List<PostListMyPageDto> GetPostListMypage(List<Post> postList){
        List<PostListMyPageDto> postListResponseDtos = new ArrayList<>();

        if(!postList.isEmpty()){
            for(Post post : postList){
                List<Comment> comment = commentRepository.findAllByPost(post);
                postListResponseDtos.add(
                        PostListMyPageDto.builder()
                                .id(post.getId())
                                .title(post.getTitle())
                                .author(post.getMember().getNickname())
                                .content(post.getContent())
                                .imgUrl(post.getImgUrl())
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
