package com.example.demo.api.dto.request.appointment;

import com.example.demo.enums.AppointmentStatus;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateAppointmentDto {

	@Future(message = "Appointment must be in the future")
	private LocalDateTime appointmentDateTme;

	private AppointmentStatus status;
}
