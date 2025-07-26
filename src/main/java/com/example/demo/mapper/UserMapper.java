package com.example.demo.mapper;

import com.example.demo.api.dto.request.user.CreateUserDto;
import com.example.demo.api.dto.request.user.UpdateUserDto;
import com.example.demo.api.dto.response.user.UserResponseDto;
import com.example.demo.persistence.entity.User;
import org.springframework.stereotype.Component;

import static com.example.demo.enums.Role.ROLE_USER;


@Component
public class UserMapper {

	public User fromCreateDto(CreateUserDto dto, String encodedPassword) {
		User user = new User();
		user.setUsername(dto.username());
		user.setEmail(dto.email());
		user.setPhone(dto.phone());
		user.setPassword(encodedPassword);
		user.setEnabled(true);
		user.setRole(ROLE_USER);

		return user;
	}

	public UserResponseDto toResponseDto(User user) {
		return new UserResponseDto(
				user.getId(),
				user.getUsername(),
				user.getEmail(),
				user.getPhone(),
				user.isEnabled(),
				user.getRole()
		);
	}

	public void updateEntityFromDto(User user, UpdateUserDto dto) {
		if (dto.getEmail() != null) user.setEmail(dto.getEmail());
		if (dto.getPhone() != null) user.setPhone(dto.getPhone());
		if (dto.getEnabled() != null) user.setEnabled(dto.getEnabled());
		if (dto.getRole() != null) user.setRole(dto.getRole());
	}
}