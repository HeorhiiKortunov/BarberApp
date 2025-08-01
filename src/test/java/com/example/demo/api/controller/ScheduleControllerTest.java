package com.example.demo.api.controller;

import com.example.demo.api.dto.response.appointment.AppointmentResponseDto;
import com.example.demo.api.dto.response.barber.BarberResponseDto;
import com.example.demo.enums.AppointmentStatus;
import com.example.demo.enums.Role;
import com.example.demo.security.WebSecurityConfig;
import com.example.demo.service.AppointmentService;
import com.example.demo.service.BarberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(WebSecurityConfig.class)
@AutoConfigureMockMvc
class ScheduleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private AppointmentService appointmentService;

	@MockitoBean
	private BarberService barberService;

	private AppointmentResponseDto appointment1;
	private AppointmentResponseDto appointment2;
	private BarberResponseDto barber;
	private LocalDateTime appointmentTime;

	@BeforeEach
	void setUp() {
		appointmentTime = LocalDateTime.now().plusDays(1);

		barber = new BarberResponseDto(5L, 10L, "Experienced barber", 30);

		appointment1 = new AppointmentResponseDto(1L, barber.id(), 20L, appointmentTime, AppointmentStatus.ACTIVE);
		appointment2 = new AppointmentResponseDto(2L, barber.id(), 21L, appointmentTime.plusHours(2), AppointmentStatus.ACTIVE);

		when(barberService.findByUserId(10L)).thenReturn(barber);
		when(appointmentService.findByBarberId(barber.id())).thenReturn(List.of(appointment1, appointment2));
	}

	@WithMockUser(userId = 10L, username = "BarberUser", roles = {Role.ROLE_BARBER})
	@Test
	void givenAuthenticatedBarber_whenGetMyAppointments_thenReturnAppointments() throws Exception {
		mockMvc.perform(get("/api/schedule/my"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].id").value(1L))
				.andExpect(jsonPath("$[1].id").value(2L));
	}

	@WithMockUser(userId = 10L, username = "BarberUser", roles = {Role.ROLE_BARBER})
	@Test
	void givenValidAppointmentId_whenCancelAppointment_thenReturnUpdatedAppointment() throws Exception {
		long appointmentId = 1L;

		var cancelledAppointment = new AppointmentResponseDto(
				appointmentId,
				barber.id(),
				20L,
				appointmentTime,
				AppointmentStatus.CANCELLED_BY_BARBER
		);

		when(appointmentService.cancelAppointmentByBarber(appointmentId))
				.thenReturn(cancelledAppointment);

		when(appointmentService.findById(appointmentId)).thenReturn(cancelledAppointment);

		mockMvc.perform(patch("/api/schedule/{id}", appointmentId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(appointmentId))
				.andExpect(jsonPath("$.status").value("CANCELLED_BY_BARBER"));
	}

	@Test
	void givenNoAuth_whenAccessEndpoints_thenUnauthorized() throws Exception {
		mockMvc.perform(get("/api/schedule/my"))
				.andExpect(status().isUnauthorized());

		mockMvc.perform(patch("/api/schedule/1"))
				.andExpect(status().isUnauthorized());
	}

	@WithMockUser(userId = 20L, username = "NotBarber", roles = {Role.ROLE_USER})
	@Test
	void givenUserWithoutBarberRole_whenAccessEndpoints_thenForbidden() throws Exception {
		mockMvc.perform(get("/api/schedule/my"))
				.andExpect(status().isForbidden());

		mockMvc.perform(patch("/api/schedule/1"))
				.andExpect(status().isForbidden());
	}
}
