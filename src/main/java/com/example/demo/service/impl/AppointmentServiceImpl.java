package com.example.demo.service.impl;

import com.example.demo.api.dto.request.appointment.CreateAppointmentDto;
import com.example.demo.api.dto.request.appointment.UpdateAppointmentDto;
import com.example.demo.api.dto.response.appointment.AppointmentResponseDto;
import com.example.demo.mapper.AppointmentMapper;
import com.example.demo.persistence.entity.Appointment;
import com.example.demo.persistence.repository.AppointmentRepository;
import com.example.demo.persistence.repository.BarberRepository;
import com.example.demo.persistence.repository.UserRepository;
import com.example.demo.service.AppointmentService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

	private final AppointmentRepository appointmentRepository;
	private final AppointmentMapper appointmentMapper;

	@Override
	public List<AppointmentResponseDto> findAllAppointments() {
		return appointmentRepository.findAll()
				.stream()
				.map(appointmentMapper::toResponseDto)
				.toList();
	}

	@Override
	public Optional<AppointmentResponseDto> findById(long id) {
		return appointmentRepository.findById(id).map(appointmentMapper::toResponseDto);
	}

	@Override
	public List<AppointmentResponseDto> findByBarberId(long id) {
		return appointmentRepository.findByBarberId(id)
				.stream()
				.map(appointmentMapper::toResponseDto)
				.toList();
	}

	@Override
	public Optional<AppointmentResponseDto> findByCustomerId(long id) {
		return appointmentRepository.findByCustomerId(id).map(appointmentMapper::toResponseDto);
	}

	@Override
	public AppointmentResponseDto createAppointment(CreateAppointmentDto dto) {
		Appointment appointment = appointmentMapper.fromCreateDto(dto);
		Appointment savedAppointment = appointmentRepository.save(appointment);
		return appointmentMapper.toResponseDto(savedAppointment);
	}

	@Override
	public AppointmentResponseDto updateAppointment(long id, UpdateAppointmentDto dto) {
		Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		if (dto.getAppointmentDateTme() != null) appointment.setAppointmentDateTime(dto.getAppointmentDateTme());
		Appointment savedAppointment = appointmentRepository.save(appointment);

		return appointmentMapper.toResponseDto(savedAppointment);
	}

	@Override
	public void deleteAppointment(long id) {
		appointmentRepository.deleteById(id);
	}
}
