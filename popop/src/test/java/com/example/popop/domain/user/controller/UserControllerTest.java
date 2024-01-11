package com.example.popop.domain.user.controller;

import com.example.popop.domain.user.dto.UserDto;
import com.example.popop.domain.user.entity.Role;
import com.example.popop.domain.user.entity.User;
import com.example.popop.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    void User_회원가입() throws Exception {
        // given
        UserDto userDto = UserDto.builder()
                .loginId("id")
                .name("mark")
                .nickname("mm")
                .password("re123")
                .email("mark@gmail.com")
                .role(Role.USER)
                .build();

        String url = "http://localhost:"+port+"/signup";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, userDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<User> all = userRepository.findAll();
        assertThat(all.get(0).getLoginId()).isEqualTo("id");
        assertThat(all.get(0).getName()).isEqualTo("mark");
    }

    @Test
    void userLogin() {
    }
}