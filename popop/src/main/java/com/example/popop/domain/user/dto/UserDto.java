package com.example.popop.domain.user.dto;

import com.example.popop.domain.user.entity.Role;
import com.example.popop.domain.user.entity.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDto {

    @Column(unique = true)
    private String loginId;

    private String name;

    @Column(unique = true)
    private String nickname;

    private String password;

    @Column(unique = true)
    private String email;

    private Role role;


    public UserDto toPassword(String encodePwd) {
        return UserDto.builder()
                .password(encodePwd)
                .build();
    }

    public User toEntity(String encodePwd) {
        return User.builder()
                .loginId(loginId)
                .name(name)
                .nickname(nickname)
                .password(encodePwd)
                .email(email)
                .role(Role.ROLE_USER)
                .build();
    }


}
