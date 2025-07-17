package com.example.demo.api.dto.request.appointment;

import com.example.demo.persistence.entity.Barber;

import java.time.LocalDateTime;

public class UpdateAppointmentDto {
	private LocalDateTime appointmentDateTme;

	public UpdateAppointmentDto() {}

	public UpdateAppointmentDto(LocalDateTime appointmentDateTme) {
		this.appointmentDateTme = appointmentDateTme;
	}

	public LocalDateTime getAppointmentDateTme() {
		return appointmentDateTme;
	}
}
