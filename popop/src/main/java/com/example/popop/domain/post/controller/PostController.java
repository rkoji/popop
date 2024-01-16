package com.example.popop.domain.post.controller;

import com.example.popop.domain.post.dto.CreatePostDto;
import com.example.popop.domain.post.service.PostService;
import com.example.popop.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final JwtUtil jwtUtil;

    @PostMapping("/create")
    public ResponseEntity<Void> createPost(@RequestPart("postDto") CreatePostDto postDto,
                                           @RequestPart(value = "file",required = false) MultipartFile file,
                                           @RequestHeader(name = AUTHORIZATION) String auth) {
        String token = auth.substring("Bearer ".length());
        try {
            postService.createPost(postDto,file,token);
            return new ResponseEntity<>(CREATED);
        } catch (Exception e) {
            log.error("Exception [Err_Msg]: {}", e.getMessage());
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }
}
