package com.example.popop.domain.user.service;

import com.example.popop.domain.user.dto.LogInDto;
import com.example.popop.domain.user.dto.UserDto;
import com.example.popop.domain.user.dto.UserLoginDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void singUpUser(UserDto userDto);

    void loginUser(UserLoginDto userLoginDto);
}
