package com.example.intermediate.service;

import com.example.intermediate.controller.request.RecommentRequestDto;
import com.example.intermediate.controller.response.RecommentResponseDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.Post;
import com.example.intermediate.domain.Recomment;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.RecommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommentService {
    private final RecommentRepository recommentRepository;
    private final TokenProvider tokenProvider;
    private final PostService postService;
    private final CommentService commentService;

    @Transactional
    public ResponseDto<?> createRecomment(RecommentRequestDto requestDto, HttpServletRequest request) {

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = postService.isPresentPost(requestDto.getPostId());
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        Comment comment = commentService.isPresentComment(requestDto.getCommentId());
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        Recomment recomment = Recomment.builder()
                .member(member)
                .post(post)
                .comment(comment)
                .content(requestDto.getContent())
                .build();
        recommentRepository.save(recomment);

        return ResponseDto.success(
                RecommentResponseDto.builder()
                        .id(recomment.getId())
                        .author(recomment.getMember().getNickname())
                        .content(recomment.getContent())
                        .createdAt(recomment.getCreatedAt())
                        .modifiedAt(recomment.getModifiedAt())
                        .build()
        );
    }
//
//    @Transactional(readOnly = true)
//    public ResponseDto<?> getAllPost() {
//        return ResponseDto.success(postRepository.findAllByOrderByModifiedAtDesc());
//    }

//    @Transactional(readOnly = true)
//    public ResponseDto<?> getAllRecommentsByComment(Long commentId) {
//
//        Comment comment = commentService.isPresentComment(commentId);
//        if (null == comment) {
//            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
//        }
//
//        List<Recomment> recommentList = recommentRepository.findAllByComment(comment);
//        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
//
//        return ResponseDto.success(recommentRepository.findAllByComment(comment));
//    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllRecommentsByComment(Long commentId) {
        Comment comment = commentService.isPresentComment(commentId);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        List<Recomment> recommentList = recommentRepository.findAllByComment(comment);
        List<RecommentResponseDto> recommentResponseDtoList = new ArrayList<>();

        for (Recomment recomment : recommentList) {
            recommentResponseDtoList.add(
                    RecommentResponseDto.builder()
                            .id(recomment.getId())
                            .author(recomment.getMember().getNickname())
                            .content(recomment.getContent())
                            .createdAt(recomment.getCreatedAt())
                            .modifiedAt(recomment.getModifiedAt())
                            .build()
            );
        }
        return ResponseDto.success(recommentResponseDtoList);
    }


    @Transactional
    public ResponseDto<?> updateRecomment(Long id, RecommentRequestDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = postService.isPresentPost(requestDto.getPostId());
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

//        Comment comment = isPresentComment(requestDto.getCommentId());
//        if (null == comment) {
//            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
//        }

        Recomment recomment = isPresentRecomment(id);
        if (null == recomment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 대댓글 id 입니다.");
        }

        if (recomment.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        recomment.update(requestDto);
        return ResponseDto.success(
                RecommentResponseDto.builder()
                        .id(recomment.getId())
                        .author(recomment.getMember().getNickname())
                        .content(recomment.getContent())
                        .createdAt(recomment.getCreatedAt())
                        .modifiedAt(recomment.getModifiedAt())
                        .build()
        );
    }


    @Transactional
    public ResponseDto<?> deleteRecomment(Long id, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

//        Comment comment = isPresentComment(id);
//        if (null == comment) {
//            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
//        }

        Recomment recomment = isPresentRecomment(id);
        if (null == recomment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 대댓글 id 입니다.");
        }

        if (recomment.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        recommentRepository.delete(recomment);
        return ResponseDto.success("success");
    }



    @Transactional(readOnly = true)
    public Recomment isPresentRecomment(Long id) {
        Optional<Recomment> optionalRecomment = recommentRepository.findById(id);
        return optionalRecomment.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

}
