package com.example.demo.service;

import com.example.demo.api.dto.request.appointment.CreateAppointmentDto;
import com.example.demo.api.dto.request.appointment.UpdateAppointmentDto;
import com.example.demo.api.dto.response.appointment.AppointmentResponseDto;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
	List<AppointmentResponseDto> findAllAppointments();
	AppointmentResponseDto findById(long id);
	List<AppointmentResponseDto> findByBarberId(long id);
	AppointmentResponseDto findByCustomerId(long id);
	AppointmentResponseDto createAppointment(CreateAppointmentDto dto);
	AppointmentResponseDto updateAppointment(long id, UpdateAppointmentDto dto);
	AppointmentResponseDto cancelAppointmentByBarber(Long id);
	AppointmentResponseDto cancelAppointmentByCustomer(Long id);
	void deleteAppointment(long id);
}
