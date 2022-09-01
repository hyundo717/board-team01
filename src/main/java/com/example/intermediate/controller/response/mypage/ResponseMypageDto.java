package com.example.intermediate.controller.response.mypage;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ResponseMypageDto {
    private boolean success;
    private List<PostListMyPageDto> postList;
    private List<CommentListMyPageDto> commentList;
    private List<RecommentListMypageDto> recommentList;

    private Error error;

    public static ResponseMypageDto success(List<PostListMyPageDto> postList, List<CommentListMyPageDto> commentList, List<RecommentListMypageDto> recommentList) {
        return new ResponseMypageDto(true, postList, commentList, recommentList, null);
    }

    public static ResponseMypageDto fail(String code, String message) {
        return new ResponseMypageDto(false, null, null, null, new Error(code, message));
    }

    @Getter
    @AllArgsConstructor
    static class Error {
        private String code;
        private String message;
    }

}
