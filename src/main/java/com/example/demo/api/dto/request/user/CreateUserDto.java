package com.example.demo.api.dto.request.user;

import java.util.List;

public record CreateUserDto(
		String username,
		String password,
		String email,
		String phone,
		List<String> authorities
) {}
