package com.example.popop.domain.user.controller;

import com.example.popop.domain.user.dto.UserDto;
import com.example.popop.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RequiredArgsConstructor
@RequestMapping
@Controller
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<Void> userSignUp(@RequestBody UserDto userDto) {
        userService.singUpUser(userDto);
        return ResponseEntity.status(NO_CONTENT).build();
    }


}
