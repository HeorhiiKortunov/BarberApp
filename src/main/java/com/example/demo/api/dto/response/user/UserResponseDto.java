package com.example.demo.api.dto.response.user;

import com.example.demo.enums.Role;

public record UserResponseDto (
		long id,
		String username,
		String email,
		String phone,
		boolean enabled,
		Role role
) {}
