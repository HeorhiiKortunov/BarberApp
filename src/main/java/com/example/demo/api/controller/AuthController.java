package com.example.demo.api.controller;

import com.example.demo.api.dto.request.auth.LoginRequest;
import com.example.demo.api.dto.request.user.CreateUserDto;
import com.example.demo.api.dto.response.auth.LoginResponse;
import com.example.demo.api.dto.response.user.UserResponseDto;
import com.example.demo.security.JwtIssuer;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final JwtIssuer jwtIssuer;

	public AuthController(JwtIssuer jwtIssuer) {
		this.jwtIssuer = jwtIssuer;
	}

	@PostMapping("/login")
	public LoginResponse login(@RequestBody @Validated LoginRequest request){
		var token = jwtIssuer.issue(1L, request.username(), "ROLE_USER");
		return new LoginResponse(token);
	}

}
//	private final UserService userService;
//
//	public AuthController(UserService userService) {
//		this.userService = userService;
//	}
//
//	@PostMapping
//	public ResponseEntity<UserResponseDto> createUser(@RequestBody CreateUserDto dto){
//		UserResponseDto responseDto = userService.createUser(dto);
//		URI location = URI.create("/api/user/" + responseDto.id());
//		return ResponseEntity.created(location).body(responseDto);
//	}

