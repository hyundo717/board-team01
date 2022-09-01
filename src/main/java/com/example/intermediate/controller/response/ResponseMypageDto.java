package com.example.intermediate.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseMypageDto<T> {
    private boolean success;
    private T writePost;
    private T writeComment;
//    private T writeRecomment;
    private T likePost;
    private T likeComment;
    private Error error;

    public static <T> ResponseMypageDto<T> success(T writePost,T writeComment, T likePost, T likeComment) {
        return new ResponseMypageDto<>(true, writePost, writeComment, likePost, likeComment, null);
    }

    public static <T> ResponseMypageDto<T> fail(String code, String message) {
        return new ResponseMypageDto<>(false, null, null, null, null, new Error(code, message));
    }

    @Getter
    @AllArgsConstructor
    static class Error {
        private String code;
        private String message;
    }

}
