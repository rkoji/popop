package com.example.popop.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EXAMPLE(BAD_REQUEST, "예시입니다."),
    DUPLICATE_NICKNAME(BAD_REQUEST, "이미 등록된 닉네임이 있습니다."),
    DUPLICATE_EMAIL(BAD_REQUEST, "이미 등록된 이메일이 있습니다."),
    DUPLICATE_USER_ID(BAD_REQUEST, "이미 등록된 아이디가 있습니다."),
    NO_EXISTS_ID(NOT_FOUND,"존재하지 않는 아이디입니다."),
    PASSWORD_MISMATCH(NOT_FOUND, "비밀번호가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
