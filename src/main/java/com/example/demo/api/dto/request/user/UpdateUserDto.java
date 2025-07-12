package com.example.demo.api.dto.request.user;

public record UpdateUserDto(
		String email,
		String phone,
		Boolean enabled
) {}
