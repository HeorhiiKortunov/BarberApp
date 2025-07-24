package com.example.demo.api.controller;

import com.example.demo.api.dto.request.user.UpdateUserDto;
import com.example.demo.api.dto.response.user.UserResponseDto;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping("/me")
	public ResponseEntity<UserResponseDto> findMyProfile(@AuthenticationPrincipal UserPrincipal principal){
		return ResponseEntity.ok(userService.findById(principal.getUserId()));
	}

	@PutMapping("/me")
	public ResponseEntity<UserResponseDto> updateMyProfile(@AuthenticationPrincipal UserPrincipal principal, UpdateUserDto dto){
		return ResponseEntity.ok(userService.updateUser(principal.getUserId(), dto));
	}

}
