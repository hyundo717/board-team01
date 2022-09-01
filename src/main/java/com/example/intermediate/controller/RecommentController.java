package com.example.intermediate.controller;

import com.example.intermediate.controller.request.RecommentRequestDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.service.RecommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Validated
@RequiredArgsConstructor
@RestController
public class RecommentController {

    private final RecommentService recommentService;

    @RequestMapping(value = "/api/auth/recomment", method = RequestMethod.POST)
    public ResponseDto<?> createRecomment(@RequestBody RecommentRequestDto requestDto,
                                          HttpServletRequest request) {
        return recommentService.createRecomment(requestDto, request);
    }

    @RequestMapping(value = "/api/recomment/{id}", method = RequestMethod.GET)
    public ResponseDto<?> getAllRecomments(@PathVariable Long id) {
        return recommentService.getAllRecommentsByComment(id);
    }

    @RequestMapping(value = "/api/auth/recomment/{id}", method = RequestMethod.PUT)
    public ResponseDto<?> updateRecomment(@PathVariable Long id, @RequestBody RecommentRequestDto requestDto,
                                          HttpServletRequest request) {
        return recommentService.updateRecomment(id, requestDto, request);
    }

    @RequestMapping(value = "/api/auth/recomment/{id}", method = RequestMethod.DELETE)
    public ResponseDto<?> deleteRecomment(@PathVariable Long id,
                                          HttpServletRequest request) {
        return recommentService.deleteRecomment(id, request);
    }

}
