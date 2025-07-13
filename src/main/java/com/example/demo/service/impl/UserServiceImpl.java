package com.example.demo.service.impl;

import com.example.demo.api.dto.request.user.CreateUserDto;
import com.example.demo.api.dto.request.user.UpdateUserDto;
import com.example.demo.api.dto.response.user.UserResponseDto;
import com.example.demo.mapper.UserMapper;
import com.example.demo.persistence.entity.User;
import com.example.demo.persistence.repository.UserRepository;
import com.example.demo.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
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
	public UserResponseDto updateUser(long id, UpdateUserDto dto) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found"));

		userMapper.updateEntityFromDto(user, dto);
		User updatedUser = userRepository.save(user);

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
	public UserResponseDto createUser(CreateUserDto dto) {
		User user = new User();
		user.setUsername(dto.username());
		//TODO: encrypt logic
		user.setPassword(dto.password());
		user.setEmail(dto.email());
		user.setPhone(dto.phone());
		user.setRole(dto.role());
		user.setEnabled(true);
		userRepository.save(user);
		return userMapper.toResponseDto(user);
	}

	@Override
	public void deleteById(long id) {
		userRepository.deleteById(id);
	}
}
