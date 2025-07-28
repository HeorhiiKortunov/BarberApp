package com.example.demo.service.impl;

import com.example.demo.api.dto.request.user.CreateUserDto;
import com.example.demo.api.dto.request.user.UpdateUserDto;
import com.example.demo.api.dto.response.user.UserResponseDto;
import com.example.demo.enums.Role;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.UsernameAlreadyExistsException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.persistence.entity.User;
import com.example.demo.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

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

	private CreateUserDto createUserDto;
	private User user;
	private UserResponseDto userResponseDto;

	@BeforeEach
	void setUp() {
		createUserDto = new CreateUserDto("john", "pass", "john@example.com", "123456789");

		user = new User();
		user.setId(1L);
		user.setUsername("john");
		user.setPassword("encoded");
		user.setEmail("john@example.com");
		user.setPhone("123456789");
		user.setEnabled(true);
		user.setRole(Role.ROLE_USER);

		userResponseDto = new UserResponseDto(1L, "john", "john@example.com", "123456", true, Role.ROLE_USER);
	}

	//tests for createUser
	@Test
	void givenValidDto_whenCreateUser_thenReturnResponseDto() {
		when(passwordEncoder.encode(any())).thenReturn("encoded");
		when(userMapper.fromCreateDto(any(), any())).thenReturn(user);
		when(userRepository.save(user)).thenReturn(user);
		when(userMapper.toResponseDto(user)).thenReturn(userResponseDto);

		UserResponseDto actual = userService.createUser(createUserDto);

		assertEquals(userResponseDto, actual);
	}

	@Test
	void givenValidDto_whenCreateUser_thenUserShouldBeSaved() {
		when(passwordEncoder.encode(any())).thenReturn("encoded");
		when(userMapper.fromCreateDto(any(), any())).thenReturn(user);
		when(userRepository.save(user)).thenReturn(user);
		when(userMapper.toResponseDto(user)).thenReturn(mock(UserResponseDto.class));

		userService.createUser(createUserDto);

		verify(userRepository).save(user);
	}

	@Test
	void shouldEncodePassword_whenCreateUser() {
		when(passwordEncoder.encode(createUserDto.password())).thenReturn("encoded");
		when(userMapper.fromCreateDto(any(), eq("encoded"))).thenReturn(user);
		when(userRepository.save(any())).thenReturn(user);
		when(userMapper.toResponseDto(any())).thenReturn(mock(UserResponseDto.class));

		userService.createUser(createUserDto);

		verify(passwordEncoder).encode(createUserDto.password());
	}

	@Test
	void usernameExists_whenCreateUser_shouldThrow() {
		when(userRepository.existsByUsername(createUserDto.username())).thenReturn(true);

		assertThrows(UsernameAlreadyExistsException.class, () -> userService.createUser(createUserDto));

		verify(userRepository, never()).save(any());
	}

	//tests for findById
	@Test
	void givenValidId_whenFindById_shouldReturnUserResponseDto() {
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(userMapper.toResponseDto(user)).thenReturn(userResponseDto);

		UserResponseDto actual = userService.findById(1L);

		assertEquals(userResponseDto, actual);
		verify(userRepository).findById(1L);
		verify(userMapper).toResponseDto(user);
	}

	@Test
	void givenInvalidId_whenFindById_shouldThrow() {
		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> userService.findById(1L));
		verify(userRepository).findById(1L);
		verifyNoInteractions(userMapper);
	}

	//tests for updateUser
	@Test
	void givenValidId_whenUpdateUser_shouldReturnUserResponseDto() {
		UpdateUserDto dto = new UpdateUserDto("newName", "1234", true, Role.ROLE_USER);
		User savedUser = new User();
		UserResponseDto expectedDto = mock(UserResponseDto.class);

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(userRepository.save(user)).thenReturn(savedUser);
		when(userMapper.toResponseDto(savedUser)).thenReturn(expectedDto);

		UserResponseDto actual = userService.updateUser(1L, dto);

		assertEquals(expectedDto, actual);
		verify(userMapper).updateEntityFromDto(user, dto);
		verify(userRepository).save(user);
		verify(userMapper).toResponseDto(savedUser);
	}

	@Test
	void givenInvalidId_whenUpdateUser_shouldThrow() {
		UpdateUserDto dto = new UpdateUserDto("test@example.com", "123456789", true, Role.ROLE_USER);
		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(1L, dto));
		verifyNoInteractions(userMapper);
		verify(userRepository).findById(1L);
	}

	//tests for deleteById
	@Test
	void givenValidId_whenDeleteById_shouldDelete() {
		when(userRepository.existsById(1L)).thenReturn(true);

		userService.deleteById(1L);

		verify(userRepository).deleteById(1L);
	}

	@Test
	void givenInvalidId_whenDeleteById_shouldThrow() {
		when(userRepository.existsById(1L)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> userService.deleteById(1L));

		verify(userRepository, never()).deleteById(anyLong());
	}

	//tests for updateUserRole
	@Test
	void givenValidId_whenUpdateUserRole_shouldReturnResponseDto() {
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(userRepository.save(user)).thenReturn(user);
		when(userMapper.toResponseDto(user)).thenReturn(userResponseDto);

		UserResponseDto actualDto = userService.updateUserRole(1L, Role.ROLE_BARBER);

		assertEquals(userResponseDto, actualDto);
	}

	@Test
	void givenValidId_whenUpdateUserRole_shouldSetNewRole() {
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(userRepository.save(user)).thenReturn(user);

		userService.updateUserRole(1L, Role.ROLE_BARBER);

		assertEquals(Role.ROLE_BARBER, user.getRole());
		verify(userRepository).save(user);
	}

	@Test
	void givenInvalidId_whenUpdateUserRole_shouldThrow() {
		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> userService.updateUserRole(1L, Role.ROLE_BARBER));
	}

	//tests for findAllUsers
	@Test
	void whenFindAllUsers_shouldReturnListOfUserResponseDtos() {
		User user2 = new User(); user2.setId(2L); user2.setUsername("Bob");
		List<User> users = List.of(user, user2);

		UserResponseDto dto1 = mock(UserResponseDto.class);
		UserResponseDto dto2 = mock(UserResponseDto.class);

		when(userRepository.findAll()).thenReturn(users);
		when(userMapper.toResponseDto(user)).thenReturn(dto1);
		when(userMapper.toResponseDto(user2)).thenReturn(dto2);

		List<UserResponseDto> result = userService.findAllUsers();

		assertEquals(2, result.size());
		assertTrue(result.contains(dto1));
		assertTrue(result.contains(dto2));

		verify(userRepository).findAll();
		verify(userMapper).toResponseDto(user);
		verify(userMapper).toResponseDto(user2);
	}

	@Test
	void givenNoUsersExist_findAllUsers_shouldReturnEmptyList() {
		when(userRepository.findAll()).thenReturn(List.of());

		List<UserResponseDto> result = userService.findAllUsers();

		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(userRepository).findAll();
		verifyNoInteractions(userMapper);
	}
}
