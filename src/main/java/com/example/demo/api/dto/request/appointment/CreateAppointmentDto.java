package com.example.demo.api.dto.request.appointment;

import java.time.LocalDateTime;

public record CreateAppointmentDto(
		long barberId,
		long customerId,
		LocalDateTime appointmentDateTime
) {}
