package com.example.demo.api.dto.request;

public record CreateUserDto(
		String username,
		String password,
		String email,
		String phone,
		String role
) {}
