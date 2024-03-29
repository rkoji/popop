package com.example.popop.domain.post.controller;

import com.example.popop.domain.post.dto.CreatePostDto;
import com.example.popop.domain.post.dto.ModifyPostDto;
import com.example.popop.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public String token(String auth) {
        return auth.substring("Bearer ".length());
    }

    // 게시글 생성
    @PostMapping("/create")
    public ResponseEntity<Void> PostCreate(@RequestPart("postDto") CreatePostDto postDto,
                                           @RequestPart(value = "file", required = false) MultipartFile file,
                                           @RequestHeader(name = AUTHORIZATION) String auth) {
        String token = token(auth);
        try {
            postService.createPost(postDto, file, token);
            return new ResponseEntity<>(CREATED);
        } catch (Exception e) {
            log.error("Exception [Err_Msg]: {}", e.getMessage());
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    // 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<Void> PostModify(@PathVariable Long postId,
                                           @RequestPart(value = "modifyPostDto", required = false) ModifyPostDto modifyPostDto,
                                           @RequestPart(value = "file", required = false) MultipartFile file,
                                           @RequestHeader(name = AUTHORIZATION) String auth) {
        String token = token(auth);
        postService.modifyPost(postId, modifyPostDto, file, token);
        return new ResponseEntity<>(OK);
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> PostDelete(@PathVariable Long postId,
                                           @RequestHeader(name = AUTHORIZATION) String auth) {
        String token = token(auth);
        postService.deletePost(postId, token);
        return new ResponseEntity<>(OK);
    }

    // 좋아요 추가
    @PostMapping("/{postId}/likes")
    public ResponseEntity<String> addLike(@PathVariable Long postId,
                                          @RequestHeader(name = AUTHORIZATION) String auth) {
        String token = token(auth);
        postService.addLike(postId, token);
        return ResponseEntity.ok("좋아요가 추가되었습니다");
    }

    // 좋아요 삭제
    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<String> deleteLike(@PathVariable Long postId,
                                            @RequestHeader(name = AUTHORIZATION) String auth) {
        String token = token(auth);
        postService.deleteLike(postId, token);
        return ResponseEntity.ok("좋아요가 삭제되었습니다");
    }
}
