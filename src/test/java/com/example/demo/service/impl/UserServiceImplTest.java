package com.example.demo.service.impl;

import com.example.demo.api.dto.request.user.CreateUserDto;
import com.example.demo.api.dto.response.user.UserResponseDto;
import com.example.demo.enums.Role;
import com.example.demo.exception.UsernameAlreadyExistsException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.persistence.entity.User;
import com.example.demo.persistence.repository.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserMapper userMapper;

	@Mock
	private PasswordEncoder passwordEncoder;

	private CreateUserDto createUserDto() {
		return new CreateUserDto("john", "pass", "john@example.com", "123456789");
	}

	@Test
	void givenValidDto_whenCreateUser_thenReturnResponseDto(){
		CreateUserDto dto = createUserDto();
		User user = new User();
		when(passwordEncoder.encode(any())).thenReturn("encoded");
		when(userMapper.fromCreateDto(any(), any())).thenReturn(user);
		when(userRepository.save(user)).thenReturn(user);

		UserResponseDto expectedDto = new UserResponseDto(1L, "john", "john@example.com", "123456",true, Role.ROLE_USER);
		when(userMapper.toResponseDto(user)).thenReturn(expectedDto);

		UserResponseDto actual = userService.createUser(dto);

		assertEquals(expectedDto, actual);

	}

	@Test
	void givenValidDto_whenCreateUser_thenUserShouldBeSaved() {
		CreateUserDto dto = createUserDto();
		User user = new User();
		when(passwordEncoder.encode(any())).thenReturn("encoded");
		when(userMapper.fromCreateDto(any(), any())).thenReturn(user);
		when(userRepository.save(user)).thenReturn(user);
		when(userMapper.toResponseDto(user)).thenReturn(mock(UserResponseDto.class));

		userService.createUser(dto);

		verify(userRepository).save(user);
	}

	@Test
	void createUser_shouldEncodePassword(){
		CreateUserDto dto = createUserDto();
		User user = new User();

		when(passwordEncoder.encode(dto.password())).thenReturn("encoded");
		when(userMapper.fromCreateDto(any(), eq("encoded"))).thenReturn(user);
		when(userRepository.save(any())).thenReturn(user);
		when(userMapper.toResponseDto(any())).thenReturn(mock(UserResponseDto.class));

		userService.createUser(dto);

		verify(passwordEncoder).encode(dto.password());
	}

	@Test
	void createUser_shouldThrow_whenUsernameExists() {
		CreateUserDto dto = createUserDto();

		when(userRepository.existsByUsername(dto.username())).thenReturn(true);

		assertThrows(UsernameAlreadyExistsException.class, () -> userService.createUser(dto));

		verify(userRepository, never()).save(any());
	}

}