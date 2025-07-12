package com.example.demo.service.impl;

import com.example.demo.api.dto.request.CreateUserDto;
import com.example.demo.api.dto.request.UpdateUserDto;
import com.example.demo.api.dto.response.UserResponseDto;
import com.example.demo.mapper.UserMapper;
import com.example.demo.persistence.entity.Users;
import com.example.demo.persistence.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	@Override
	public Optional<UserResponseDto> findById(Long id) {
		return userRepository.findById(id).map(userMapper::toResponseDto);
	}

	@Override
	public Optional<UserResponseDto> findByUsername(String username) {
		return userRepository.findByUsername(username).map(userMapper::toResponseDto);
	}

	@Override
	public UserResponseDto updateUser(String username, UpdateUserDto updateUserDto) {
		Users user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found"));

		userMapper.updateEntityFromDto(user, updateUserDto);
		Users updatedUser = userRepository.save(user);

		return userMapper.toResponseDto(updatedUser);
	}

	@Override
	public List<UserResponseDto> getAllUsers() {
		return userRepository.findAll()
				.stream()
				.map(userMapper::toResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	public void createUser(CreateUserDto createUserDto) {
		Users user = new Users();
		user.setUsername(createUserDto.username());
		//TODO: encrypt logic
		user.setPassword(createUserDto.password());
		user.setEmail(createUserDto.email());
		user.setPhone(createUserDto.phone());
		user.setRole(createUserDto.role());
		user.setEnabled(true);
	}
}
