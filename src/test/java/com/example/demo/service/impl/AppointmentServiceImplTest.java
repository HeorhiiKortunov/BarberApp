package com.example.demo.service.impl;

import com.example.demo.api.dto.request.appointment.CreateAppointmentDto;
import com.example.demo.api.dto.request.appointment.UpdateAppointmentDto;
import com.example.demo.api.dto.response.appointment.AppointmentResponseDto;
import com.example.demo.enums.AppointmentStatus;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.AppointmentMapper;
import com.example.demo.persistence.entity.Appointment;
import com.example.demo.persistence.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceImplTest {

	private AppointmentRepository appointmentRepository;
	private AppointmentMapper appointmentMapper;
	private AppointmentServiceImpl appointmentService;

	@BeforeEach
	void setUp() {
		appointmentRepository = mock(AppointmentRepository.class);
		appointmentMapper = mock(AppointmentMapper.class);
		appointmentService = new AppointmentServiceImpl(appointmentRepository, appointmentMapper);
	}

	@Test
	void findAllAppointments_shouldReturnListOfDtos() {
		Appointment appointment = new Appointment();
		AppointmentResponseDto dto = new AppointmentResponseDto(1L, 2L, 3L, LocalDateTime.now(), AppointmentStatus.ACTIVE);

		when(appointmentRepository.findAll()).thenReturn(List.of(appointment));
		when(appointmentMapper.toResponseDto(appointment)).thenReturn(dto);

		var result = appointmentService.findAllAppointments();

		assertEquals(1, result.size());
		assertEquals(dto, result.get(0));
	}

	@Test
	void findById_existingId_shouldReturnDto() {
		long id = 1L;
		Appointment appointment = new Appointment();
		AppointmentResponseDto expectedDto = new AppointmentResponseDto(id, 2L, 3L, LocalDateTime.now(), AppointmentStatus.ACTIVE);

		when(appointmentRepository.findById(id)).thenReturn(Optional.of(appointment));
		when(appointmentMapper.toResponseDto(appointment)).thenReturn(expectedDto);

		AppointmentResponseDto result = appointmentService.findById(id);

		assertEquals(expectedDto.id(), result.id());
		assertEquals(expectedDto.barberId(), result.barberId());
		assertEquals(expectedDto.customerId(), result.customerId());
		assertEquals(expectedDto.status(), result.status());
	}

	@Test
	void findById_nonExistingId_shouldThrowException() {
		when(appointmentRepository.findById(anyLong())).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> appointmentService.findById(1L));
	}

	@Test
	void findByBarberId_shouldReturnListOfDtos() {
		Appointment appointment = new Appointment();
		AppointmentResponseDto dto = new AppointmentResponseDto(1L, 2L, 3L, LocalDateTime.now(), AppointmentStatus.ACTIVE);

		when(appointmentRepository.findByBarberId(2L)).thenReturn(List.of(appointment));
		when(appointmentMapper.toResponseDto(appointment)).thenReturn(dto);

		var result = appointmentService.findByBarberId(2L);

		assertEquals(1, result.size());
		assertEquals(dto, result.get(0));
	}

	@Test
	void findByCustomerId_existing_shouldReturnDto() {
		Appointment appointment = new Appointment();
		AppointmentResponseDto dto = new AppointmentResponseDto(1L, 2L, 3L, LocalDateTime.now(), AppointmentStatus.ACTIVE);

		when(appointmentRepository.findByCustomerId(3L)).thenReturn(Optional.of(appointment));
		when(appointmentMapper.toResponseDto(appointment)).thenReturn(dto);

		var result = appointmentService.findByCustomerId(3L);

		assertEquals(dto, result);
	}

	@Test
	void findByCustomerId_notFound_shouldThrow() {
		when(appointmentRepository.findByCustomerId(3L)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> appointmentService.findByCustomerId(3L));
	}

	@Test
	void createAppointment_shouldReturnSavedDto() {
		CreateAppointmentDto createDto = mock(CreateAppointmentDto.class);
		Appointment appointment = new Appointment();
		Appointment savedAppointment = new Appointment();
		AppointmentResponseDto responseDto = new AppointmentResponseDto(1L, 2L, 3L, LocalDateTime.now(), AppointmentStatus.ACTIVE);

		when(appointmentMapper.fromCreateDto(createDto)).thenReturn(appointment);
		when(appointmentRepository.save(appointment)).thenReturn(savedAppointment);
		when(appointmentMapper.toResponseDto(savedAppointment)).thenReturn(responseDto);

		var result = appointmentService.createAppointment(createDto);

		assertEquals(responseDto, result);
	}

	@Test
	void updateAppointment_existingId_shouldUpdateAndReturnDto() {
		long id = 1L;
		Appointment appointment = new Appointment();
		UpdateAppointmentDto dto = mock(UpdateAppointmentDto.class);
		AppointmentResponseDto responseDto = new AppointmentResponseDto(id, 2L, 3L, LocalDateTime.now(), AppointmentStatus.ACTIVE);

		when(appointmentRepository.findById(id)).thenReturn(Optional.of(appointment));
		when(appointmentRepository.save(appointment)).thenReturn(appointment);
		when(appointmentMapper.toResponseDto(appointment)).thenReturn(responseDto);

		var result = appointmentService.updateAppointment(id, dto);

		verify(appointmentMapper).updateAppointmentFromDto(appointment, dto);
		assertEquals(responseDto, result);
	}

	@Test
	void cancelAppointmentByBarber_shouldSetStatusAndReturnDto() {
		Appointment appointment = new Appointment();
		appointment.setId(1L);
		appointment.setStatus(AppointmentStatus.ACTIVE);
		AppointmentResponseDto expectedDto = new AppointmentResponseDto(1L, 2L, 3L, LocalDateTime.now(), AppointmentStatus.CANCELLED_BY_BARBER);

		when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
		when(appointmentRepository.save(appointment)).thenReturn(appointment);
		when(appointmentMapper.toResponseDto(appointment)).thenReturn(expectedDto);

		var result = appointmentService.cancelAppointmentByBarber(1L);

		assertEquals(AppointmentStatus.CANCELLED_BY_BARBER, appointment.getStatus());
		assertEquals(expectedDto, result);
	}

	@Test
	void cancelAppointmentByCustomer_shouldSetStatusAndReturnDto() {
		Appointment appointment = new Appointment();
		appointment.setId(1L);
		appointment.setStatus(AppointmentStatus.ACTIVE);
		AppointmentResponseDto expectedDto = new AppointmentResponseDto(1L, 2L, 3L, LocalDateTime.now(), AppointmentStatus.CANCELLED_BY_CUSTOMER);

		when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
		when(appointmentRepository.save(appointment)).thenReturn(appointment);
		when(appointmentMapper.toResponseDto(appointment)).thenReturn(expectedDto);

		var result = appointmentService.cancelAppointmentByCustomer(1L);

		assertEquals(AppointmentStatus.CANCELLED_BY_CUSTOMER, appointment.getStatus());
		assertEquals(expectedDto, result);
	}

	@Test
	void deleteAppointment_shouldCallRepository() {
		appointmentService.deleteAppointment(1L);
		verify(appointmentRepository).deleteById(1L);
	}
}
