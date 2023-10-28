package com.ai.petraitserver.image.service;

import com.ai.petraitserver.image.entity.Image;
import com.ai.petraitserver.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final WebClient webClient = WebClient.create("http://localhost:8090/api");

    @Async
    public void convertImage(MultipartFile image) throws IOException {
        byte[] imageData = image.getBytes();
        byte[] convertedImage = callImageConversionAPI(imageData).block();

        String fileName = "converted-" + image.getOriginalFilename();
        Path path = Paths.get(fileName);
        Files.write(path, convertedImage);

        Image newImage = Image.builder()
                .imageUrl(convertedImage.toString()) // 수정필요
                .build();
        imageRepository.save(newImage);
    }

    private Mono<byte[]> callImageConversionAPI(byte[] imageData) {
        return webClient.post()
                .uri("/convert-image")  // API path
                .bodyValue(imageData)
                .retrieve()
                .bodyToMono(byte[].class);
    }
}
