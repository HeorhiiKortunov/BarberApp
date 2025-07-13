package com.example.demo.mapper;

import com.example.demo.api.dto.request.appointment.CreateAppointmentDto;
import com.example.demo.api.dto.request.appointment.UpdateAppointmentDto;
import com.example.demo.api.dto.response.appointment.AppointmentResponseDto;
import com.example.demo.persistence.entity.Appointment;
import com.example.demo.persistence.entity.Barber;
import com.example.demo.persistence.entity.User;
import com.example.demo.persistence.repository.BarberRepository;
import com.example.demo.persistence.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

	UserRepository userRepository;
	BarberRepository barberRepository;

	public AppointmentMapper(UserRepository userRepository, BarberRepository barberRepository) {
		this.userRepository = userRepository;
		this.barberRepository = barberRepository;
	}


	public Appointment fromCreateDto(CreateAppointmentDto dto){
		Appointment appointment = new Appointment();
		Barber barber = barberRepository.findById(dto.barberId()).orElseThrow(() -> new RuntimeException("Barber not found"));
		User customer = userRepository.findById(dto.customerId()).orElseThrow(() -> new RuntimeException("User not found"));

		appointment.setBarber(barber);
		appointment.setCustomer(customer);
		appointment.setAppointmentDateTime(dto.appointmentDateTime());

		return appointment;
	}

	public AppointmentResponseDto toResponseDto(Appointment appointment){
		return new AppointmentResponseDto(
				appointment.getId(),
				appointment.getBarber().getId(),
				appointment.getCustomer().getId(),
				appointment.getAppointmentDateTime()
		);
	}

	public void updateAppointmentFromDto(Appointment appointment, UpdateAppointmentDto dto){
		if(dto.getAppointmentDateTme() != null) appointment.setAppointmentDateTime(dto.getAppointmentDateTme()); ;
	}
}
