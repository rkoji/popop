package com.example.popop.domain.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostStatus {

    PUBLISHED("게시"),
    UNPUBLISHED("비게시"),
    DELETED("삭제");

    private final String status;

}

