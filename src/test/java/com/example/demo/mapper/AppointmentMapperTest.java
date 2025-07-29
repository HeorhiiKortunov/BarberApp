package com.example.demo.mapper;

import com.example.demo.api.dto.request.appointment.CreateAppointmentDto;
import com.example.demo.api.dto.request.appointment.UpdateAppointmentDto;
import com.example.demo.api.dto.response.appointment.AppointmentResponseDto;
import com.example.demo.enums.AppointmentStatus;
import com.example.demo.persistence.entity.Appointment;
import com.example.demo.persistence.entity.Barber;
import com.example.demo.persistence.entity.User;
import com.example.demo.persistence.repository.BarberRepository;
import com.example.demo.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentMapperTest {

	private UserRepository userRepository;
	private BarberRepository barberRepository;
	private AppointmentMapper appointmentMapper;

	@BeforeEach
	void setUp() {
		userRepository = mock(UserRepository.class);
		barberRepository = mock(BarberRepository.class);
		appointmentMapper = new AppointmentMapper(userRepository, barberRepository);
	}

	@Test
	void fromCreateDto_shouldMapCorrectly() {
		long barberId = 1L;
		long customerId = 2L;
		LocalDateTime dateTime = LocalDateTime.of(2025, 8, 1, 12, 0);
		AppointmentStatus status = AppointmentStatus.ACTIVE;

		Barber barber = new Barber();
		barber.setId(barberId);
		User customer = new User();
		customer.setId(customerId);

		CreateAppointmentDto dto = new CreateAppointmentDto(barberId, customerId, dateTime, status);

		when(barberRepository.findById(barberId)).thenReturn(Optional.of(barber));
		when(userRepository.findById(customerId)).thenReturn(Optional.of(customer));

		Appointment result = appointmentMapper.fromCreateDto(dto);

		assertEquals(barber, result.getBarber());
		assertEquals(customer, result.getCustomer());
		assertEquals(dateTime, result.getAppointmentDateTime());
		assertEquals(status, result.getStatus());
	}

	@Test
	void fromCreateDto_barberNotFound_shouldThrow() {
		CreateAppointmentDto dto = new CreateAppointmentDto(1L, 2L, LocalDateTime.now(), AppointmentStatus.ACTIVE);

		when(barberRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> appointmentMapper.fromCreateDto(dto));
	}

	@Test
	void fromCreateDto_customerNotFound_shouldThrow() {
		long barberId = 1L;
		long customerId = 2L;

		CreateAppointmentDto dto = new CreateAppointmentDto(barberId, customerId, LocalDateTime.now(), AppointmentStatus.ACTIVE);

		when(barberRepository.findById(barberId)).thenReturn(Optional.of(new Barber()));
		when(userRepository.findById(customerId)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> appointmentMapper.fromCreateDto(dto));
	}

	@Test
	void toResponseDto_shouldMapCorrectly() {
		Barber barber = new Barber();
		barber.setId(10L);

		User customer = new User();
		customer.setId(20L);

		Appointment appointment = new Appointment();
		appointment.setId(100L);
		appointment.setBarber(barber);
		appointment.setCustomer(customer);
		appointment.setAppointmentDateTime(LocalDateTime.of(2025, 8, 1, 15, 30));
		appointment.setStatus(AppointmentStatus.CANCELLED_BY_CUSTOMER);

		AppointmentResponseDto dto = appointmentMapper.toResponseDto(appointment);

		assertEquals(100L, dto.id());
		assertEquals(10L, dto.barberId());
		assertEquals(20L, dto.customerId());
		assertEquals(AppointmentStatus.CANCELLED_BY_CUSTOMER, dto.status());
		assertEquals(appointment.getAppointmentDateTime(), dto.appointmentDateTime());
	}

	@Test
	void updateAppointmentFromDto_shouldUpdateOnlyNonNullFields() {
		Appointment appointment = new Appointment();
		appointment.setAppointmentDateTime(LocalDateTime.of(2025, 8, 1, 10, 0));
		appointment.setStatus(AppointmentStatus.ACTIVE);

		LocalDateTime newTime = LocalDateTime.of(2025, 8, 2, 14, 0);
		AppointmentStatus newStatus = AppointmentStatus.CANCELLED_BY_BARBER;

		UpdateAppointmentDto dto = new UpdateAppointmentDto(newTime, newStatus);

		appointmentMapper.updateAppointmentFromDto(appointment, dto);

		assertEquals(newTime, appointment.getAppointmentDateTime());
		assertEquals(newStatus, appointment.getStatus());
	}
}
