package com.example.demo.api.dto.request;

public record UpdateUserDto(
		String email,
		String phone,
		Boolean enabled
) {}
