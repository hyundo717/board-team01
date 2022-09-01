package com.example.intermediate.controller;

import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class LikeController {
    private final LikeService likeService;

    @RequestMapping(value = "/api/auth/like/post/{id}", method = RequestMethod.POST)
    public ResponseDto<?> likePost(@PathVariable Long id, HttpServletRequest request) {
        return likeService.likePost(id, request);
    }

    @RequestMapping(value = "/api/auth/like/comment/{id}", method = RequestMethod.POST)
    public ResponseDto<?> likeComment(@PathVariable Long id, HttpServletRequest request) {
        return likeService.likeComment(id, request);
    }

    @RequestMapping(value = "/api/auth/like/recomment/{id}", method = RequestMethod.POST)
    public ResponseDto<?> likeRecomment(@PathVariable Long id, HttpServletRequest request) {
        return likeService.likeRecomment(id, request);
    }
}
