package com.example.demo.api.controller;

import com.example.demo.api.dto.request.barber.CreateBarberDto;
import com.example.demo.api.dto.request.barber.UpdateBarberDto;
import com.example.demo.api.dto.request.user.UpdateUserDto;
import com.example.demo.api.dto.response.barber.BarberResponseDto;
import com.example.demo.api.dto.response.user.UserResponseDto;
import com.example.demo.service.BarberService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
	private final BarberService barberService;
	private final UserService userService;

	@GetMapping("/user/{id}")
	public ResponseEntity<UserResponseDto> findUser(@PathVariable Long id) {
		return ResponseEntity.ok(userService.findById(id));
	}

	@GetMapping("/user")
	public ResponseEntity<List<UserResponseDto>> findAllUsers(){
		return ResponseEntity.ok(userService.findAllUsers());
	}

	@PatchMapping("/user/{id}")
	public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto dto){
		return ResponseEntity.ok(userService.updateUser(id, dto));
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		userService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/barber/{id}")
	public ResponseEntity<BarberResponseDto> findBarber(@PathVariable Long id){
		return ResponseEntity.ok(barberService.findById(id));
	}

	@GetMapping("/barber")
	public ResponseEntity<List<BarberResponseDto>> findAllBarbers(){
		return ResponseEntity.ok(barberService.findAllBarbers());
	}

	@PostMapping("/barber")
	public ResponseEntity<BarberResponseDto> createBarber(@RequestBody CreateBarberDto dto){
		BarberResponseDto createdBarber = barberService.createBarber(dto);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(createdBarber.id())
				.toUri();

		return ResponseEntity.created(location).body(createdBarber);
	}

	@PatchMapping("/barber/{id}")
	public ResponseEntity<BarberResponseDto> updateBarber(@PathVariable Long id, @RequestBody UpdateBarberDto dto){
		return ResponseEntity.ok(barberService.updateBarber(id, dto));
	}

	@DeleteMapping("/barber/{id}")
	public ResponseEntity<Void> deleteBarber(@PathVariable Long id){
		barberService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
