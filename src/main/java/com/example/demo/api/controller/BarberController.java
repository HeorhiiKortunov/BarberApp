package com.example.demo.api.controller;

import com.example.demo.api.dto.request.barber.UpdateBarberDto;
import com.example.demo.api.dto.response.barber.BarberResponseDto;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.BarberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/barber")
@AllArgsConstructor
public class BarberController {
	private final BarberService barberService;

	@GetMapping("/me")
	public ResponseEntity<BarberResponseDto> findMyProfile(@AuthenticationPrincipal UserPrincipal principal){
		return ResponseEntity.ok(barberService.findById(barberService.findByUserId(principal.getUserId()).id()));
	}

	@PutMapping("/me")
	public ResponseEntity<BarberResponseDto> updateMyProfile(@AuthenticationPrincipal UserPrincipal principal, UpdateBarberDto dto){
		return ResponseEntity.ok(barberService.updateBarber(barberService.findByUserId(principal.getUserId()).id(), dto));
	}

}
