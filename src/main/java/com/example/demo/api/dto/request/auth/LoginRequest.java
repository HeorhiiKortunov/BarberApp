package com.example.demo.api.dto.request.auth;


public record LoginRequest(
		String username,
		String password
) {}
