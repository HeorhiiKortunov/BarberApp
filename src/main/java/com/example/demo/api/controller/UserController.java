package com.example.demo.api.controller;

import com.example.demo.api.dto.response.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@GetMapping("/users/{id}")
	public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
		Optional<UserResponseDto> userOpt = userService.findById(id);
		return userOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}


}
