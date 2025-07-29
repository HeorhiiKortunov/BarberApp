package com.example.demo.api.controller;

import com.example.demo.security.WebSecurityConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
@Import(WebSecurityConfig.class)
@AutoConfigureMockMvc
class AdminControllerTest {

}