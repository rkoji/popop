package com.example.popop.domain.user.service.impl;

import com.example.popop.domain.user.dto.UserDto;
import com.example.popop.domain.user.repository.UserRepository;
import com.example.popop.domain.user.service.UserService;
import com.example.popop.global.exception.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.popop.global.exception.ErrorCode.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public void singUpUser(UserDto userDto) {

        // 아이디 중복
        userRepository.findLoginIdByLoginId(userDto.getLoginId()).ifPresent(
                existingLoginId -> {
                    throw new CustomException(DUPLICATE_USER_ID);
                });

        // 닉네임 중복
        userRepository.findByNicknameByNickname(userDto.getNickname()).ifPresent(
                existingNickname -> {
                    throw new CustomException(DUPLICATE_NICKNAME);
                });

        // 이메일 중복
        userRepository.findByEmailByEmail(userDto.getEmail()).ifPresent(
                existingEmail -> {
                    throw new CustomException(DUPLICATE_EMAIL);
                });

        // password 암호화
        String password = userDto.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePwd = encoder.encode(password);

        userRepository.save(userDto.toEntity(encodePwd));
    }
}
