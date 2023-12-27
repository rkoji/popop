package com.example.popop.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EXAMPLE(HttpStatus.BAD_REQUEST, "예시입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
