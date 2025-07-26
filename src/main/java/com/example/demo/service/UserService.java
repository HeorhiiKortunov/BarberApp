package com.example.demo.service;


import com.example.demo.api.dto.request.user.CreateUserDto;
import com.example.demo.api.dto.request.user.UpdateUserDto;
import com.example.demo.api.dto.response.user.UserResponseDto;
import com.example.demo.enums.Role;

import java.util.List;

public interface UserService {
    UserResponseDto findById(long id);
	UserResponseDto updateUser(long id, UpdateUserDto dto);
	UserResponseDto updateUserRole(Long userId, Role newRole);
    List<UserResponseDto> findAllUsers();
    UserResponseDto createUser(CreateUserDto dto);
	void deleteById(long id);
}