package com.example.popop.domain.post.service;

import com.example.popop.domain.post.dto.CreatePostDto;
import com.example.popop.domain.post.dto.ModifyPostDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface PostService {

    void createPost(CreatePostDto createPostDto , MultipartFile file, String token);

    void modifyPost(Long postId, ModifyPostDto modifyPostDto, MultipartFile file, String token);

    void deletePost(Long postId, String token);

    void addLike(Long postId, String token);

    void deleteLike(Long postId, String token);
}
