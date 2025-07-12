package com.example.demo.api.controller;

import com.example.demo.api.dto.response.user.UserResponseDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/users/{id}")
	public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
		Optional<UserResponseDto> userOpt = userService.findById(id);
		return userOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}


}
