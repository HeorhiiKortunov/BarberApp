package com.example.demo.api.dto.response.appointment;

import java.time.LocalDateTime;

public record AppointmentResponseDto(
		long id,
		long barberId,
		long customerId,
		LocalDateTime appointmentDateTime
) {}
