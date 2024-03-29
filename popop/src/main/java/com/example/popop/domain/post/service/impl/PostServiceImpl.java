package com.example.popop.domain.post.service.impl;

import com.example.popop.domain.post.dto.CreatePostDto;
import com.example.popop.domain.post.dto.ModifyPostDto;
import com.example.popop.domain.post.entity.Post;
import com.example.popop.domain.post.entity.PostStatus;
import com.example.popop.domain.post.repository.PostRepository;
import com.example.popop.domain.post.service.ImageService;
import com.example.popop.domain.post.service.PostService;
import com.example.popop.domain.user.entity.User;
import com.example.popop.domain.user.repository.UserRepository;
import com.example.popop.global.exception.CustomException;
import com.example.popop.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.example.popop.global.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final JwtUtil jwtUtil;

    // 게시글 생성
    @Override
    public void createPost(CreatePostDto createPostDto, MultipartFile file, String token) {
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

    // 게시글 수정
    @Transactional
    @Override
    public void modifyPost(Long postId, ModifyPostDto modifyPostDto, MultipartFile file, String token) {
        postRepository.findById(postId).orElseThrow(() -> new CustomException(NO_EXISTS_POST_ID));

        String loginId = jwtUtil.getLoginId(token);

        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new CustomException(NO_EXISTS_ID));
        Post post = postRepository.findByAuthor(user).orElseThrow(() -> new CustomException(USER_MISTMATCH));

        if (file != null && !file.isEmpty()) {
            String savedImage = imageService.saveImage(file);
            post.modifyAttachments(savedImage);
        }

        if (modifyPostDto != null) {
            post.modifyPostForm(modifyPostDto);
        }
    }

    // 게시글 삭제
    @Override
    public void deletePost(Long postId, String token) {
        String loginId = jwtUtil.getLoginId(token);
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new CustomException(NO_EXISTS_ID));
        Post post = postRepository.findByAuthor(user).orElseThrow(() -> new CustomException(USER_MISTMATCH));

        Post existPost
                = postRepository.findById(postId).orElseThrow(() -> new CustomException(NO_EXISTS_POST_ID));
        postRepository.delete(existPost);
    }

    // 좋아요 추가
    @Override
    public void addLike(Long postId, String token) {
        String loginId = jwtUtil.getLoginId(token);
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new CustomException(NO_EXISTS_ID));
        Post post = postRepository.findByAuthor(user).orElseThrow(() -> new CustomException(USER_MISTMATCH));

        if (post.addLike(user)) {
            postRepository.save(post);
        }
    }

    // 좋아요 삭제
    @Override
    public void deleteLike(Long postId, String token) {
        String loginId = jwtUtil.getLoginId(token);
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new CustomException(NO_EXISTS_ID));
        Post post = postRepository.findByAuthor(user).orElseThrow(() -> new CustomException(USER_MISTMATCH));

        if (post.deleteLike(user)) {
            postRepository.save(post);
        }
    }
}

