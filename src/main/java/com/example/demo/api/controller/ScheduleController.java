package com.example.demo.api.controller;

import com.example.demo.api.dto.response.appointment.AppointmentResponseDto;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.AppointmentService;
import com.example.demo.service.BarberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/schedule")
@AllArgsConstructor
//For barber controlling his schedule
public class ScheduleController {
	private final AppointmentService  appointmentService;
	private final BarberService barberService;

	@GetMapping("/my")
	public ResponseEntity<List<AppointmentResponseDto>> findMyAppointments(@AuthenticationPrincipal UserPrincipal principal){
		return ResponseEntity.ok(appointmentService.findByBarberId(barberService.findByUserId(principal.getUserId()).id()));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<AppointmentResponseDto> cancelAppointment(@PathVariable Long id){
		appointmentService.cancelAppointmentByBarber(id);
		return ResponseEntity.ok(appointmentService.findById(id));
	}
}
