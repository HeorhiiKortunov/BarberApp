package com.example.demo.api.dto.request.appointment;

import com.example.demo.persistence.entity.Barber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateAppointmentDto {
	private LocalDateTime appointmentDateTme;
}
