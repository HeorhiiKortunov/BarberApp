package com.example.demo.mapper;

import com.example.demo.api.dto.request.user.CreateUserDto;
import com.example.demo.api.dto.request.user.UpdateUserDto;
import com.example.demo.api.dto.response.user.UserResponseDto;
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
		if (dto.getEmail() != null) user.setEmail(dto.getEmail());
		if (dto.getPhone() != null) user.setPhone(dto.getPhone());
		if (dto.getEnabled() != null) user.setEnabled(dto.getEnabled());
	}
}