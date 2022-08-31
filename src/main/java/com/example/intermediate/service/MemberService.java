package com.example.intermediate.service;

import com.example.intermediate.controller.response.*;
import com.example.intermediate.controller.response.mypage.PostListMyPageDto;
import com.example.intermediate.controller.response.mypage.ResponseMypageDto;
import com.example.intermediate.domain.Member;
import com.example.intermediate.controller.request.LoginRequestDto;
import com.example.intermediate.controller.request.MemberRequestDto;
import com.example.intermediate.controller.request.TokenDto;
import com.example.intermediate.domain.Post;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.CommentRepository;
import com.example.intermediate.repository.LikePostRepository;
import com.example.intermediate.repository.MemberRepository;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.intermediate.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

  private final MemberRepository memberRepository;

  private final LikePostRepository likePostRepository;

  private final PostRepository postRepository;

  private final CommentRepository commentRepository;

  private final PasswordEncoder passwordEncoder;

  private final TokenProvider tokenProvider;

  @Transactional
  public ResponseDto<?> createMember(MemberRequestDto requestDto) {
    if (null != isPresentMember(requestDto.getNickname())) {
      return ResponseDto.fail("DUPLICATED_NICKNAME",
          "중복된 닉네임 입니다.");
    }

    if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
      return ResponseDto.fail("PASSWORDS_NOT_MATCHED",
          "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    }

    Member member = Member.builder()
            .nickname(requestDto.getNickname())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                    .build();
    memberRepository.save(member);
    return ResponseDto.success(
        MemberResponseDto.builder()
            .id(member.getId())
            .nickname(member.getNickname())
            .createdAt(member.getCreatedAt())
            .modifiedAt(member.getModifiedAt())
            .build()
    );
  }

  @Transactional
  public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
    Member member = isPresentMember(requestDto.getNickname());
    if (null == member) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
          "사용자를 찾을 수 없습니다.");
    }

    if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
      return ResponseDto.fail("INVALID_MEMBER", "사용자를 찾을 수 없습니다.");
    }

    TokenDto tokenDto = tokenProvider.generateTokenDto(member);
    tokenToHeaders(tokenDto, response);

    return ResponseDto.success(
        MemberResponseDto.builder()
            .id(member.getId())
            .nickname(member.getNickname())
            .createdAt(member.getCreatedAt())
            .modifiedAt(member.getModifiedAt())
            .build()
    );
  }

  public ResponseDto<?> logout(HttpServletRequest request) {
    if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
      return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
    }
    Member member = tokenProvider.getMemberFromAuthentication();
    if (null == member) {
      return ResponseDto.fail("MEMBER_NOT_FOUND",
          "사용자를 찾을 수 없습니다.");
    }

    return tokenProvider.deleteRefreshToken(member);
  }

  public ResponseMypageDto mypageWrite(HttpServletRequest request) {
    if (null == request.getHeader("Refresh-Token")) {
      return ResponseMypageDto.fail("MEMBER_NOT_FOUND",
              "로그인이 필요합니다.");
    }

    if (null == request.getHeader("Authorization")) {
      return ResponseMypageDto.fail("MEMBER_NOT_FOUND",
              "로그인이 필요합니다.");
    }

    Member member = validateMember(request);
    if (null == member) {
      return ResponseMypageDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
    }

    ListResponseProvider listResponse = new ListResponseProvider(commentRepository);

    // 사용자가 작성한 게시글 리스트
    List<Post> writePostsList = postRepository.findAllByMember(member);
    List<PostListMyPageDto> postListMypageDtos = listResponse.GetPostListMypage(writePostsList);
    
    // 사용자가 작성한 댓글 리스트
    
    // 사용자가 작성한 대댓글 리스트

    // 사용자가 좋아요한 게시글 리스트
//    List<LikePost> likePosts = likePostRepository.findAllByMember(member);

    // 사용자가 좋아요한 댓글 리스트
//    if(likePosts.isEmpty()){
//      return ResponseDto.success(null);
//    } else {
//      List<PostResponseDto> postResponseDtoList = new ArrayList<>();
//      for (LikePost likePost : likePosts){
//        Long postId = likePost.getPostId();
//        postRepository.findById(postId).ifPresent(post -> postResponseDtoList.add(
//                PostResponseDto.builder()
//                        .id(postId)
//                        .title(post.getTitle())
//                        .content(post.getContent())
//                        .author(post.getMember().getNickname())
//                        .createdAt(post.getCreatedAt())
//                        .modifiedAt(post.getModifiedAt())
//                        .build()
//        ));
//      }
//      return ResponseMypageDto.success(postResponseDtoList);
//    }
    return ResponseMypageDto.fail("","");
  }

  @Transactional
  public Member validateMember(HttpServletRequest request) {
    if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
      return null;
    }
    return tokenProvider.getMemberFromAuthentication();
  }


  @Transactional(readOnly = true)
  public Member isPresentMember(String nickname) {
    Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
    return optionalMember.orElse(null);
  }

  public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
    response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
    response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
    response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
  }

}
