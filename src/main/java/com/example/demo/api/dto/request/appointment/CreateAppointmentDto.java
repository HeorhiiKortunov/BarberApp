package com.example.demo.api.dto.request.appointment;

import com.example.demo.enums.AppointmentStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateAppointmentDto(

		@Min(value = 1, message = "Barber ID must be greater than 0")
		long barberId,

		@Min(value = 1, message = "Customer ID must be greater than 0")
		long customerId,

		@NotNull(message = "Appointment date is required")
		@Future(message = "Appointment must be in the future")
		LocalDateTime appointmentDateTime,

		@NotNull(message = "Status is required")
		AppointmentStatus status
) {}
