package com.example.demo.mapper;

import com.example.demo.api.dto.request.CreateUserDto;
import com.example.demo.api.dto.request.UpdateUserDto;
import com.example.demo.api.dto.response.UserResponseDto;
import com.example.demo.persistence.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

	public Users fromCreateDto(CreateUserDto dto, String encodedPassword) {
		Users user = new Users();
		user.setUsername(dto.username());
		user.setEmail(dto.email());
		user.setPhone(dto.phone());
		user.setPassword(encodedPassword);
		user.setEnabled(true);
		user.setRole(dto.role() != null ? dto.role() : "ROLE_CUSTOMER");
		return user;
	}

	public UserResponseDto toResponseDto(Users user) {
		return new UserResponseDto(
				user.getUsername(),
				user.getEmail(),
				user.getPhone(),
				user.isEnabled()
		);
	}

	public void updateEntityFromDto(Users user, UpdateUserDto dto) {
		if (dto.email() != null) user.setEmail(dto.email());
		if (dto.phone() != null) user.setPhone(dto.phone());
		if (dto.enabled() != null) user.setEnabled(dto.enabled());
	}
}