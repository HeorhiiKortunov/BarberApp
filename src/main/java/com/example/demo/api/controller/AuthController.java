package com.example.demo.api.controller;

import com.example.demo.api.dto.request.auth.LoginRequest;
import com.example.demo.api.dto.request.user.CreateUserDto;
import com.example.demo.api.dto.response.auth.LoginResponse;
import com.example.demo.api.dto.response.user.UserResponseDto;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	private final UserService userService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequest request){
		return ResponseEntity.ok(authService.attemptLogin(request.username(), request.password()));
	}

	@PostMapping("/register")
	public ResponseEntity<UserResponseDto> register(@RequestBody @Validated CreateUserDto dto){
		UserResponseDto responseDto = userService.createUser(dto);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(responseDto.id())
				.toUri();

		return ResponseEntity.created(location).body(responseDto);
	}

}