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
import com.example.demo.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserResponseDto findById(long id) {
		return userRepository.findById(id).map(userMapper::toResponseDto)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

	@Override
	public UserResponseDto updateUser(long id, UpdateUserDto dto) {
		var user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		userMapper.updateEntityFromDto(user, dto);
		var savedUser = userRepository.save(user);

		return userMapper.toResponseDto(savedUser);
	}

	@Override
	public void deleteById(long id) {
		userRepository.deleteById(id);
	}

	@Override
	public UserResponseDto updateUserRole(Long userId, Role newRole) {
		var user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		user.setRole(newRole);
		return userMapper.toResponseDto(userRepository.save(user));
	}

	@Override
	public List<UserResponseDto> findAllUsers() {
		return userRepository.findAll()
				.stream()
				.map(userMapper::toResponseDto)
				.toList();
	}

	@Override
	public UserResponseDto createUser(CreateUserDto dto) {
		if (userRepository.existsByUsername(dto.username())) {
			throw new UsernameAlreadyExistsException("Username already taken");
		}
		var user = userMapper.fromCreateDto(dto, passwordEncoder.encode(dto.password()));
		var savedUser = userRepository.save(user);
		return userMapper.toResponseDto(savedUser);
	}

}
