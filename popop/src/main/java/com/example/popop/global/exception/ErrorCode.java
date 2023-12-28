package com.example.popop.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EXAMPLE(HttpStatus.BAD_REQUEST, "예시입니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "이미 등록된 닉네임이 있습니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "이미 등록된 이메일이 있습니다."),
    DUPLICATE_USER_ID(HttpStatus.BAD_REQUEST, "이미 등록된 아이디가 있습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
