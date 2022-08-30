package com.example.intermediate.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.intermediate.controller.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.dir}")
    private String dir;

    private final AmazonS3Client s3Client;

    public ResponseDto<?> uploadFile(MultipartFile multipartFile) throws IOException {

        InputStream inputStream = multipartFile.getInputStream();
        String originFileName = multipartFile.getOriginalFilename();

        String s3FileName = UUID.randomUUID() + "-" + originFileName;

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getSize());

        s3Client.putObject(bucket, s3FileName, inputStream, objMeta);

        String imgUrl = s3Client.getUrl(bucket, dir + s3FileName).toString();

        return ResponseDto.success(imgUrl);
    }
}
