package com.example.intermediate.controller;

import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/api/post/image")
    public ResponseDto<?> uploadFile(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        return imageService.uploadFile(multipartFile);
    }
}
