package com.example.popop.domain.user.controller;

import com.example.popop.domain.user.dto.UserDto;
import com.example.popop.domain.user.dto.UserLoginDto;
import com.example.popop.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RequiredArgsConstructor
@RequestMapping
@Controller
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> userSignUp(@RequestBody UserDto userDto) {
        userService.singUpUser(userDto);
        return ResponseEntity.status(OK).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok().body(userService.loginUser(userLoginDto));
    }
}
