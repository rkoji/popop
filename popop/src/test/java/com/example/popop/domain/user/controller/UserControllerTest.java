package com.example.popop.domain.user.controller;

import com.example.popop.domain.user.dto.UserDto;
import com.example.popop.domain.user.dto.UserLoginDto;
import com.example.popop.domain.user.entity.Role;
import com.example.popop.domain.user.entity.User;
import com.example.popop.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String url = "http://localhost:";

    @Test
    void User_회원가입() throws Exception {
        // given
        UserDto userDto = UserDto.builder()
                .loginId("id")
                .name("mark")
                .nickname("mm")
                .password("re123")
                .email("mark@gmail.com")
                .role(Role.ROLE_USER)
                .build();

        url = url + port + "/signup";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, userDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<User> all = userRepository.findAll();
        assertThat(all.get(0).getLoginId()).isEqualTo("id");
        assertThat(all.get(0).getName()).isEqualTo("mark");
    }

    @Test
    void User_로그인_성공() throws Exception {
        // given
        UserLoginDto userLoginDto = UserLoginDto.builder()
                .loginId("id")
                .password("re123")
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLoginDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void User_로그인_실패_잘못된_패스워드() throws Exception {
        // given
        UserLoginDto userLoginDto = UserLoginDto.builder()
                .loginId("id")
                .password("wrongPassword")
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLoginDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void User_로그인_실패_존재하지_않는_아이디() throws Exception {
        // given
        UserLoginDto userLoginDto = UserLoginDto.builder()
                .loginId("nonexistentId")
                .password("somePassword")
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLoginDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}