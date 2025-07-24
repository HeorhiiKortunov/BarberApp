package com.example.demo.service.impl;

import com.example.demo.api.dto.request.appointment.CreateAppointmentDto;
import com.example.demo.api.dto.request.appointment.UpdateAppointmentDto;
import com.example.demo.api.dto.response.appointment.AppointmentResponseDto;
import com.example.demo.enums.AppointmentStatus;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.AppointmentMapper;
import com.example.demo.persistence.entity.Appointment;
import com.example.demo.persistence.repository.AppointmentRepository;
import com.example.demo.service.AppointmentService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
	public AppointmentResponseDto findById(long id) {
		return appointmentRepository.findById(id).map(appointmentMapper::toResponseDto)
				.orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
	}

	@Override
	public List<AppointmentResponseDto> findByBarberId(long id) {
		return appointmentRepository.findByBarberId(id)
				.stream()
				.map(appointmentMapper::toResponseDto)
				.toList();
	}

	@Override
	public AppointmentResponseDto findByCustomerId(long id) {
		return appointmentRepository.findByCustomerId(id).map(appointmentMapper::toResponseDto)
				.orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
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
		appointmentMapper.updateAppointmentFromDto(appointment, dto);
		Appointment savedAppointment = appointmentRepository.save(appointment);

		return appointmentMapper.toResponseDto(savedAppointment);
	}

	@Override
	public AppointmentResponseDto cancelAppointmentByBarber(Long id) {
		Appointment appointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Appointment not found"));
		appointment.setStatus(AppointmentStatus.CANCELLED_BY_BARBER);
		appointmentRepository.save(appointment);

		return appointmentMapper.toResponseDto(appointment);
	}

	@Override
	public AppointmentResponseDto cancelAppointmentByCustomer(Long id) {
		Appointment appointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Appointment not found"));
		appointment.setStatus(AppointmentStatus.CANCELLED_BY_CUSTOMER);
		appointmentRepository.save(appointment);

		return appointmentMapper.toResponseDto(appointment);
	}

	@Override
	public void deleteAppointment(long id) {
		appointmentRepository.deleteById(id);
	}
}
