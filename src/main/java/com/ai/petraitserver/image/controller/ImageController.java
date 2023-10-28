package com.ai.petraitserver.image.controller;

import com.ai.petraitserver.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/convert")
    public ResponseEntity convertImage(@RequestPart MultipartFile image) throws IOException {
        imageService.convertImage(image);
        return ResponseEntity.ok().build();
    }
}
