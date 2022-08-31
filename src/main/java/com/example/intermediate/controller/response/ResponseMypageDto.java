package com.example.intermediate.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseMypageDto<T> {
    private boolean success;
    private T post;
    private T comment;
    private T recomment;

    private Error error;

    public static <T> ResponseMypageDto<T> success(T post,T comment, T reomment) {
        return new ResponseMypageDto<>(true, post, comment, recomment, null);
    }

    public static <T> ResponseMypageDto<T> fail(String code, String message) {
        return new ResponseMypageDto<>(false, null, null, null, new Error(code, message));
    }

    @Getter
    @AllArgsConstructor
    static class Error {
        private String code;
        private String message;
    }

}
