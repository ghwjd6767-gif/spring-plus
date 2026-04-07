package org.example.expert.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.service.S3Service;
import org.example.expert.domain.user.dto.response.UserFileDownloadUrlResponse;
import org.example.expert.domain.user.dto.response.UserFileUploadResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

@RestController
@RequiredArgsConstructor
public class UserFileController {

    private final S3Service s3Service;

    @PostMapping("/files/upload")
    public ResponseEntity<UserFileUploadResponse> upload(@RequestParam("file") MultipartFile file) {
        String key = s3Service.upload(file);
        return ResponseEntity.ok(new UserFileUploadResponse(key));
    }

    @GetMapping("/files/download-url")
    public ResponseEntity<UserFileDownloadUrlResponse> getDownloadUrl(@RequestParam String key) {
        URL url = s3Service.getDownloadUrl(key);
        return ResponseEntity.ok(new UserFileDownloadUrlResponse(url.toString()));
    }
}