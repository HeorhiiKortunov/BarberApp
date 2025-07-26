package com.example.demo.service;


import com.example.demo.api.dto.request.user.CreateUserDto;
import com.example.demo.api.dto.request.user.UpdateUserDto;
import com.example.demo.api.dto.response.user.UserResponseDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponseDto findById(long id);
	UserResponseDto updateUser(long id, UpdateUserDto dto);
	UserResponseDto updateUserRole(Long userId, String newRole);
    List<UserResponseDto> findAllUsers();
    UserResponseDto createUser(CreateUserDto dto);
	void deleteById(long id);
}