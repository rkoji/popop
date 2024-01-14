package com.example.popop.domain.user.service;

import com.example.popop.domain.user.dto.UserDto;
import com.example.popop.domain.user.dto.UserLoginDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void singUpUser(UserDto userDto);

    String loginUser(UserLoginDto userLoginDto);

}
