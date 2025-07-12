package com.example.demo.api.dto.response.user;

public record UserResponseDto (
		String username,
		String email,
		String phone,
		Boolean enable
) {}
