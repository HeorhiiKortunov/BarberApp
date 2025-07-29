package com.example.demo.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {
	@Autowired
	private MockMvc api;

	//register tests
	@Test
	void givenValidUser_whenRegister_thenCanLoginSuccessfully() throws Exception {
		String registerJson = """
        {
          "username": "John",
          "password": "secret",
          "email": "john@example.com",
          "phone": "1234567890"
        }
    """;

		// register user
		api.perform(post("/api/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(registerJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.email").value("john@example.com"));

		String loginJson = """
        {
          "username": "John",
          "password": "secret"
        }
    """;

		// login with same user
		api.perform(post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(loginJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accessToken").isNotEmpty());
	}

	@Test
	void givenInvalidCredentials_whenLogin_thenUnauthorized() throws Exception {
		String json = """
        {
          "username": "wrong",
          "password": "wrong"
        }
        """;

		api.perform(post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isUnauthorized());
	}

}