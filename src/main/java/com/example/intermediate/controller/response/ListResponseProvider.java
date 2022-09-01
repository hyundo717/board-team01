package com.example.intermediate.controller.response;

import com.example.intermediate.controller.response.mypage.CommentListMyPageDto;
import com.example.intermediate.controller.response.mypage.PostListMyPageDto;
import com.example.intermediate.controller.response.mypage.RecommentListMypageDto;
import com.example.intermediate.domain.*;
import com.example.intermediate.repository.CommentRepository;
import com.example.intermediate.repository.PostRepository;
import com.example.intermediate.repository.RecommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ListResponseProvider {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final RecommentRepository recommentRepository;

    public List<PostListResponseDto> getPostListResponse(List<Post> postList){
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

    public PostListMyPageDto buildPostListMypageDto(Post post, List<Comment> comments, String author){
        return PostListMyPageDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .author(author)
                .content(post.getContent())
                .imgUrl(post.getImgUrl())
                .likesNum(post.getLikesNum())
                .commentsNum(comments.size())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }

    public CommentListMyPageDto buildCommentListMypageDto(Comment comment, List<Recomment> recomments, String author){
        return CommentListMyPageDto.builder()
                .commentId(comment.getId())
                .postId(comment.getPost().getId())
                .author(author)
                .content(comment.getContent())
                .likesNum(comment.getLikesNum())
                .recommentsNum(recomments.size())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }

    public RecommentListMypageDto buildRecommentListMypageDto(Recomment recomment, String author){
        return RecommentListMypageDto.builder()
                .recommentId(recomment.getId())
                .commentId(recomment.getComment().getId())
                .postId(recomment.getPost().getId())
                .author(author)
                .content(recomment.getContent())
                .likesNum(recomment.getLikesNum())
                .createdAt(recomment.getCreatedAt())
                .modifiedAt(recomment.getModifiedAt())
                .build();
    }

    public List<PostListMyPageDto> getPostListMypage(List<Post> postList, String author){
        List<PostListMyPageDto> postListMyPageDtos = new ArrayList<>();

        if(!postList.isEmpty()){
            for(Post post : postList){
                List<Comment> comments = commentRepository.findAllByPost(post);
                postListMyPageDtos.add(buildPostListMypageDto(post,comments,author));
            }
        }
        return postListMyPageDtos;
    }

    public List<CommentListMyPageDto> getCommentListMypage(List<Comment> commentList, String author){
        List<CommentListMyPageDto> commentListMyPageDtos = new ArrayList<>();

        if(!commentList.isEmpty()){
            for(Comment comment: commentList){
                List<Recomment> recomments = recommentRepository.findAllByComment(comment);
                commentListMyPageDtos.add(buildCommentListMypageDto(comment,recomments,author));
            }
        }
        return commentListMyPageDtos;
    }

    public List<RecommentListMypageDto> getRecommentListMypage(List<Recomment> recommentList, String author){
        List<RecommentListMypageDto> recommentListMyPagetDtos = new ArrayList<>();

        if(!recommentList.isEmpty()){
            for(Recomment recomment: recommentList){
                recommentListMyPagetDtos.add(buildRecommentListMypageDto(recomment, author));
            }
        }
        return recommentListMyPagetDtos;
    }

    public List<PostListMyPageDto> getLikePosts(List<LikePost> likePosts){
        List<PostListMyPageDto> postListMyPageDtos = new ArrayList<>();

        for(LikePost likePost : likePosts){
            Long postId = likePost.getPostId();
            Post post = postRepository.findById(postId).orElse(null);

            if(post == null){throw new RuntimeException();}

            List<Comment> comments = commentRepository.findAllByPost(post);

            String author = post.getMember().getNickname();

            postListMyPageDtos.add(buildPostListMypageDto(post,comments,author));
        }
        return postListMyPageDtos;
    }

    public List<CommentListMyPageDto> getLikeComments(List<LikeCo> likeComments){
        List<CommentListMyPageDto> commentListMyPageDtos = new ArrayList<>();

        for(LikeCo likeComment : likeComments){
            Long commentId = likeComment.getCommentId();
            Comment comment = commentRepository.findById(commentId).orElse(null);

            if(comment == null){throw new RuntimeException();}

            List<Recomment> recomments = recommentRepository.findAllByComment(comment);

            String author = comment.getMember().getNickname();

            commentListMyPageDtos.add(buildCommentListMypageDto(comment,recomments,author));
        }
        return commentListMyPageDtos;
    }

    public List<RecommentListMypageDto> getLikeRecomments(List<LikeReco> likeRecomments){
        List<RecommentListMypageDto> recommentListMyPageDtos = new ArrayList<>();

        for(LikeReco likeRecomment : likeRecomments){
            Long recommentId = likeRecomment.getRecommentId();
            Recomment recomment = recommentRepository.findById(recommentId).orElse(null);

            if(recomment == null) {throw new RuntimeException();}

            String author = recomment.getMember().getNickname();

            recommentListMyPageDtos.add(buildRecommentListMypageDto(recomment,author));
        }
        return recommentListMyPageDtos;
    }

//    public Object GetPostListResponse(List<Post> postList) {
//    }
}
