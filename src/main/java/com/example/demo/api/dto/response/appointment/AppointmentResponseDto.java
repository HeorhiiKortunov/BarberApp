package com.example.demo.api.dto.response.appointment;

import com.example.demo.enums.AppointmentStatus;

import java.time.LocalDateTime;

public record AppointmentResponseDto(
		long id,
		long barberId,
		long customerId,
		LocalDateTime appointmentDateTime,
		AppointmentStatus status
) {}
