package com.example.demo.api.dto.request.user;


public record CreateUserDto(
		String username,
		String password,
		String email,
		String phone
) {}
