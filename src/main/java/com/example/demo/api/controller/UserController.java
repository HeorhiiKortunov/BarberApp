package com.example.demo.api.controller;

import com.example.demo.api.dto.request.user.CreateUserDto;
import com.example.demo.api.dto.request.user.UpdateUserDto;
import com.example.demo.api.dto.response.user.UserResponseDto;
import com.example.demo.persistence.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDto> findUser(@PathVariable Long id) {
		Optional<UserResponseDto> userOpt = userService.findById(id);
		return userOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<UserResponseDto>> findAllUsers(){
		return ResponseEntity.ok(userService.findAllUsers());
	}

	@PostMapping
	public ResponseEntity<UserResponseDto> createUser(@RequestBody CreateUserDto dto){
		UserResponseDto responseDto = userService.createUser(dto);
		URI location = URI.create("/api/user/" + responseDto.id());
		return ResponseEntity.created(location).body(responseDto);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto dto){
		return ResponseEntity.ok(userService.updateUser(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		userService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
