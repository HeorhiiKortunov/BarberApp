package com.example.demo.api.controller;

import com.example.demo.api.dto.request.appointment.CreateAppointmentDto;
import com.example.demo.api.dto.response.appointment.AppointmentResponseDto;
import com.example.demo.api.dto.response.barber.BarberResponseDto;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.AppointmentService;
import com.example.demo.service.BarberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/appointment")
@RequiredArgsConstructor
//for controlling appointments by user
public class AppointmentController {
	private final AppointmentService appointmentService;
	private final BarberService barberService;

	@GetMapping("/my")
	public ResponseEntity<AppointmentResponseDto> findMyAppointment(@AuthenticationPrincipal UserPrincipal principal){
		return ResponseEntity.ok(appointmentService.findByCustomerId(principal.getUserId()));
	}

	@PostMapping
	public ResponseEntity<AppointmentResponseDto> createAppointment(@AuthenticationPrincipal UserPrincipal principal, @RequestBody CreateAppointmentDto dto){
		var schedule = appointmentService.findByBarberId(barberService.findByUserId(principal.getUserId()).id());
		for (AppointmentResponseDto i : schedule){
			if (!dto.appointmentDateTime().plusHours(1).isBefore(i.appointmentDateTime())
							|| !dto.appointmentDateTime().isAfter(i.appointmentDateTime().plusHours(1))){
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment overlaps with existing");
			}
		}
		AppointmentResponseDto response = appointmentService.createAppointment(dto);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(response.id())
				.toUri();

		return ResponseEntity.created(location).body(response);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<AppointmentResponseDto> cancelAppointment(@PathVariable Long id){
		appointmentService.cancelAppointmentByCustomer(id);
		return ResponseEntity.ok(appointmentService.findById(id));
	}

	@GetMapping("/barber/{id}")
	public ResponseEntity<BarberResponseDto> findBarber(@PathVariable Long id){
		return ResponseEntity.ok(barberService.findById(id));
	}

	@GetMapping("/barber")
	public ResponseEntity<List<BarberResponseDto>> findAllBarbers(){
		return ResponseEntity.ok(barberService.findAllBarbers());
	}



}
