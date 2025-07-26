package com.example.demo.service;

import com.example.demo.api.dto.response.auth.LoginResponse;

public interface AuthService {
	LoginResponse attemptLogin(String username, String password);
}
