package com.example.demo.api.dto.request.appointment;

import com.example.demo.enums.AppointmentStatus;

import java.time.LocalDateTime;

public record CreateAppointmentDto(
		long barberId,
		long customerId,
		LocalDateTime appointmentDateTime,
		AppointmentStatus status
) {}
