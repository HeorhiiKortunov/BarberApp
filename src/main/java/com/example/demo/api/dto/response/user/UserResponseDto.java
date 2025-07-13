package com.example.demo.api.dto.response.user;

public record UserResponseDto (
		long id,
		String username,
		String email,
		String phone,
		boolean enabled
) {}
