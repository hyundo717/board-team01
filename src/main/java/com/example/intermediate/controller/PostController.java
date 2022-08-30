package com.example.intermediate.controller;

import com.example.intermediate.controller.request.PostRequestDto;
import com.example.intermediate.controller.request.PostWriteDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.service.PostService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class PostController {

  private final PostService postService;

//  @RequestMapping(value = "/api/auth/post", method = RequestMethod.POST)
//  public ResponseDto<?> createPost(@RequestBody PostWriteDto requestDto,
//      HttpServletRequest request) {
//    return postService.createPost(requestDto, request);
//  }

  @RequestMapping(value = "/api/auth/post", method = RequestMethod.POST)
  public ResponseDto<?> createPost(@ModelAttribute PostRequestDto postRequestDto,
                                   HttpServletRequest request) throws IOException {
    MultipartFile multipartFile = postRequestDto.getMultipartFile();
    PostWriteDto postWriteDto = postRequestDto.getPostWriteDto();
    System.out.println(postWriteDto.getTitle());


    return postService.createPost(multipartFile, postWriteDto, request);
  }

  @RequestMapping(value = "/api/post/{id}", method = RequestMethod.GET)
  public ResponseDto<?> getPost(@PathVariable Long id) {
    return postService.getPost(id);
  }

  @RequestMapping(value = "/api/post", method = RequestMethod.GET)
  public ResponseDto<?> getAllPosts() {
    return postService.getAllPost();
  }

  @RequestMapping(value = "/api/auth/post/{id}", method = RequestMethod.PUT)
  public ResponseDto<?> updatePost(@PathVariable Long id, @RequestBody PostWriteDto postWriteDto,
      HttpServletRequest request) {
    return postService.updatePost(id, postWriteDto, request);
  }

  @RequestMapping(value = "/api/auth/post/{id}", method = RequestMethod.DELETE)
  public ResponseDto<?> deletePost(@PathVariable Long id,
      HttpServletRequest request) {
    return postService.deletePost(id, request);
  }

//  @RequestMapping(value = "/api/auth/post/img/{id}", method = RequestMethod.POST)
//  public ResponseDto<?> uploadFile(@PathVariable Long id, @RequestParam("images") MultipartFile multipartFile, @RequestParam String fileSize, HttpServletRequest request) throws IOException {
//    return postService.uploadFile(id, multipartFile, fileSize, request);
//  }

}
