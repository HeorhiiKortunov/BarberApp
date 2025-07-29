package com.example.demo.api.controller;

import com.example.demo.api.dto.response.user.UserResponseDto;
import com.example.demo.enums.Role;
import com.example.demo.security.*;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(WebSecurityConfig.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;


    @Test
    void givenNoAuth_whenGetMyProfile_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/user/me"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser
    @Test
    void givenAuth_whenGetMyProfile_thenReturnUser() throws Exception {
        var userResponse = new UserResponseDto(
                1L,
                "John",
                "john@example.com",
                "1234567890",
                true,
                Role.ROLE_USER
        );

        when(userService.findById(anyLong())).thenReturn(userResponse);

        mockMvc.perform(get("/api/user/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.phone").value("1234567890"))
                .andExpect(jsonPath("$.enabled").value(true))
                .andExpect(jsonPath("$.role").value("ROLE_USER"));
    }

    @WithMockUser
    @Test
    void givenAuth_whenUpdateMyProfile_thenReturnUpdatedUser() throws Exception {
        String updateJson = """
            {
              "email": "new@example.com",
              "phone": "9876543210",
              "enabled": true,
              "role": "ROLE_USER"
            }
        """;

        var updatedUser = new UserResponseDto(
                1L,
                "John",
                "new@example.com",
                "+42100000000",
                true,
                Role.ROLE_USER
        );

        when(userService.updateUser(anyLong(), any())).thenReturn(updatedUser);

        mockMvc.perform(put("/api/user/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("John"))
                .andExpect(jsonPath("$.email").value("new@example.com"))
                .andExpect(jsonPath("$.phone").value("+42100000000"))
                .andExpect(jsonPath("$.enabled").value(true))
                .andExpect(jsonPath("$.role").value("ROLE_USER"));
    }

    @WithMockUser
    @Test
    void givenInvalidData_whenUpdateMyProfile_thenBadRequest() throws Exception {
        String invalidJson = """
            {
              "email": "not-an-email",
              "phone": "",
              "enabled": true,
              "role": "ROLE_USER"
            }
        """;

        mockMvc.perform(put("/api/user/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}
