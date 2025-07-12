package com.example.demo.service;


import com.example.demo.api.dto.request.user.CreateUserDto;
import com.example.demo.api.dto.request.user.UpdateUserDto;
import com.example.demo.api.dto.response.user.UserResponseDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserResponseDto> findById(Long id);
	Optional<UserResponseDto> findByUsername(String username);
	UserResponseDto updateUser(String username, UpdateUserDto userResponseDto);
    List<UserResponseDto> getAllUsers();
    void createUser(CreateUserDto userResponseDto);
	void deleteByUsername(String username);
}