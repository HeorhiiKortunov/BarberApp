package com.example.demo.api.dto.response;

public record UserResponseDto(
		String username,
		String email,
		String phone,
		Boolean enabled
) {}
