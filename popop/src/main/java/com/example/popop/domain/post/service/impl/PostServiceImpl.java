package com.example.popop.domain.post.service.impl;

import com.example.popop.domain.post.dto.CreatePostDto;
import com.example.popop.domain.post.entity.Post;
import com.example.popop.domain.post.entity.PostStatus;
import com.example.popop.domain.post.repository.PostRepository;
import com.example.popop.domain.post.service.ImageService;
import com.example.popop.domain.post.service.PostService;
import com.example.popop.domain.user.entity.User;
import com.example.popop.domain.user.repository.UserRepository;
import com.example.popop.global.exception.CustomException;
import com.example.popop.global.exception.ErrorCode;
import com.example.popop.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.example.popop.global.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final JwtUtil jwtUtil;


    @Override
    public void createPost(CreatePostDto createPostDto , MultipartFile file, String token) {
        String savedImage = imageService.saveImage(file);
        String loginId = jwtUtil.getLoginId(token);
        User user = userRepository.findByLoginId(loginId).orElseThrow(() ->
                new CustomException(NO_EXISTS_ID));

        Post post = Post.builder()
                .title(createPostDto.getTitle())
                .content(createPostDto.getContent())
                .author(user)
                .address(createPostDto.getAddress())
                .attachments(savedImage)
                .status(PostStatus.PUBLISHED)
                .isPopup(true)
                .popupStartDate(createPostDto.getPopupStartDate())
                .popupEndDate(createPostDto.getPopupEndDate())
                .build();

        postRepository.save(post);
    }

}

