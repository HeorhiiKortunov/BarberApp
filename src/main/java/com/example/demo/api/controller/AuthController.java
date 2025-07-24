package com.example.demo.api.controller;

import com.example.demo.api.dto.request.auth.LoginRequest;
import com.example.demo.api.dto.request.user.CreateUserDto;
import com.example.demo.api.dto.response.auth.LoginResponse;
import com.example.demo.api.dto.response.user.UserResponseDto;
import com.example.demo.security.JwtIssuer;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
	private final JwtIssuer jwtIssuer;
	private final AuthenticationManager authenticationManager;
	private final UserService userService;

	@PostMapping("/login")
	public LoginResponse login(@RequestBody @Validated LoginRequest request){
		var authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.username(), request.password())
		);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		var principal = (UserPrincipal) authentication.getPrincipal();

		var roles = principal.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.toList();

		var token = jwtIssuer.issue(principal.getUserId(), principal.getUsername(), roles);
		return new LoginResponse(token);
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