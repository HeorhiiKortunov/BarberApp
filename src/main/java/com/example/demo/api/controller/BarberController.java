package com.example.demo.api.controller;

import com.example.demo.api.dto.request.barber.CreateBarberDto;
import com.example.demo.api.dto.request.barber.UpdateBarberDto;
import com.example.demo.api.dto.response.barber.BarberResponseDto;
import com.example.demo.service.BarberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/barber")
public class BarberController {
	private final BarberService barberService;

	public BarberController(BarberService barberService) {
		this.barberService = barberService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<BarberResponseDto> findBarber(@PathVariable Long id){
		return barberService.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<BarberResponseDto> createBarber(@RequestBody CreateBarberDto dto){
		return ResponseEntity.ok(barberService.createBarber(dto));
	}

	@GetMapping
	public ResponseEntity<List<BarberResponseDto>> findAllBarbers(){
		return ResponseEntity.ok(barberService.findAllBarbers());
	}

	@PatchMapping("/{id}")
	public ResponseEntity<BarberResponseDto> updateBarber(@PathVariable Long id, @RequestBody UpdateBarberDto dto){
		return ResponseEntity.ok(barberService.updateBarber(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBarber(@PathVariable Long id){
		barberService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
