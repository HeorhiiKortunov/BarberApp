package com.example.demo.mapper;

import com.example.demo.api.dto.request.user.CreateUserDto;
import com.example.demo.api.dto.request.user.UpdateUserDto;
import com.example.demo.api.dto.response.user.UserResponseDto;
import com.example.demo.persistence.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.demo.enums.Role.ROLE_ADMIN;
import static com.example.demo.enums.Role.ROLE_USER;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

	private UserMapper userMapper;

	@BeforeEach
	void setUp() {
		userMapper = new UserMapper();
	}

	@Test
	void fromCreateDto_shouldMapFieldsCorrectly() {
		CreateUserDto dto = new CreateUserDto("john", "pass", "john@example.com", "1234567890");
		String encodedPassword = "encoded_pass";

		User user = userMapper.fromCreateDto(dto, encodedPassword);

		assertEquals("john", user.getUsername());
		assertEquals("john@example.com", user.getEmail());
		assertEquals("1234567890", user.getPhone());
		assertEquals("encoded_pass", user.getPassword());
		assertTrue(user.isEnabled());
		assertEquals(ROLE_USER, user.getRole());
	}

	@Test
	void toResponseDto_shouldMapUserToDtoCorrectly() {
		User user = new User();
		user.setId(1L);
		user.setUsername("alice");
		user.setEmail("alice@example.com");
		user.setPhone("0987654321");
		user.setEnabled(false);
		user.setRole(ROLE_ADMIN);

		UserResponseDto dto = userMapper.toResponseDto(user);

		assertEquals(1L, dto.id());
		assertEquals("alice", dto.username());
		assertEquals("alice@example.com", dto.email());
		assertEquals("0987654321", dto.phone());
		assertFalse(dto.enabled());
		assertEquals(ROLE_ADMIN, dto.role());
	}

	@Test
	void updateEntityFromDto_shouldUpdateOnlyNonNullFields() {
		User user = new User();
		user.setEmail("old@example.com");
		user.setPhone("0000000000");
		user.setEnabled(true);
		user.setRole(ROLE_USER);

		UpdateUserDto dto = new UpdateUserDto("new@example.com", null, false, ROLE_ADMIN);

		userMapper.updateEntityFromDto(user, dto);

		assertEquals("new@example.com", user.getEmail());
		assertEquals("0000000000", user.getPhone());
		assertFalse(user.isEnabled());
		assertEquals(ROLE_ADMIN, user.getRole());
	}
}
