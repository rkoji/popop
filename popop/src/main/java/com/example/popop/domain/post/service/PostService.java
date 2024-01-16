package com.example.popop.domain.post.service;

import com.example.popop.domain.post.dto.CreatePostDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface PostService {

    void createPost(CreatePostDto createPostDto , MultipartFile file, String token);
}
