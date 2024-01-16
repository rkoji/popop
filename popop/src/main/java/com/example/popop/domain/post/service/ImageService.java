package com.example.popop.domain.post.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    @Value("${upload.directory}")
    private String uploadDirectory;

    public String saveImage(MultipartFile file) {
        try {
            if (file == null) {
                throw new IllegalArgumentException("MultipartFile is null");
            }

            Path uploadPath = Paths.get(uploadDirectory).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }

//    public String saveImage(MultipartFile file) {
//        try {
//            Path uploadPath = Paths.get(uploadDirectory).toAbsolutePath().normalize();
//            Files.createDirectories(uploadPath);
//
//            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//
//            Path filePath = uploadPath.resolve(fileName);
//            Files.copy(file.getInputStream(), filePath);
//
//            return fileName;
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to save image", e);
//        }
//    }
}