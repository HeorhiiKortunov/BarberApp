package com.example.demo.api.controller;

import com.example.demo.api.dto.request.appointment.CreateAppointmentDto;
import com.example.demo.api.dto.response.appointment.AppointmentResponseDto;
import com.example.demo.api.dto.response.barber.BarberResponseDto;
import com.example.demo.enums.AppointmentStatus;
import com.example.demo.security.WebSecurityConfig;
import com.example.demo.service.AppointmentService;
import com.example.demo.service.BarberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(WebSecurityConfig.class)
@AutoConfigureMockMvc
class AppointmentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private AppointmentService appointmentService;

	@MockitoBean
	private BarberService barberService;

	private BarberResponseDto defaultBarber;
	private LocalDateTime appointmentTime;

	@BeforeEach
	void setup() {
		appointmentTime = LocalDateTime.now().plusDays(1);
		defaultBarber = new BarberResponseDto(2L, 1L, "Experienced barber", 25); // userId = 1L to match default @WithMockUser

		when(barberService.findByUserId(1L)).thenReturn(defaultBarber);
	}

	@WithMockUser
	@Test
	void givenAuthenticatedUser_whenGetMyAppointment_thenReturnAppointment() throws Exception {
		var appointment = new AppointmentResponseDto(
				1L,
				defaultBarber.id(),
				1L,
				appointmentTime,
				AppointmentStatus.ACTIVE
		);

		when(appointmentService.findByCustomerId(1L)).thenReturn(appointment);

		mockMvc.perform(get("/api/appointment/my"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.barberId").value(defaultBarber.id()))
				.andExpect(jsonPath("$.customerId").value(1L))
				.andExpect(jsonPath("$.status").value("ACTIVE"));
	}

	@WithMockUser
	@Test
	void givenValidAppointment_whenCreate_thenReturnCreated() throws Exception {
		String createJson = String.format("""
            {
              "barberId": %d,
              "customerId": 1,
              "appointmentDateTime": "%s"
            }
            """, defaultBarber.id(), appointmentTime.toString());

		when(appointmentService.findByBarberId(defaultBarber.id())).thenReturn(List.of()); // no overlaps
		when(appointmentService.createAppointment(any(CreateAppointmentDto.class)))
				.thenReturn(new AppointmentResponseDto(5L, defaultBarber.id(), 1L, appointmentTime, AppointmentStatus.ACTIVE));

		mockMvc.perform(post("/api/appointment")
						.contentType(MediaType.APPLICATION_JSON)
						.content(createJson))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", org.hamcrest.Matchers.containsString("/api/appointment/5")))
				.andExpect(jsonPath("$.id").value(5L))
				.andExpect(jsonPath("$.barberId").value(defaultBarber.id()))
				.andExpect(jsonPath("$.customerId").value(1L))
				.andExpect(jsonPath("$.status").value("ACTIVE"));
	}

	@WithMockUser
	@Test
	void givenOverlappingAppointment_whenCreate_thenBadRequest() throws Exception {
		String createJson = String.format("""
            {
              "barberId": %d,
              "customerId": 1,
              "appointmentDateTime": "%s"
            }
            """, defaultBarber.id(), appointmentTime.toString());

		var overlappingAppointment = new AppointmentResponseDto(1L, defaultBarber.id(), 1L, appointmentTime, AppointmentStatus.ACTIVE);
		when(appointmentService.findByBarberId(defaultBarber.id())).thenReturn(List.of(overlappingAppointment));

		mockMvc.perform(post("/api/appointment")
						.contentType(MediaType.APPLICATION_JSON)
						.content(createJson))
				.andExpect(status().isBadRequest());
	}

	@WithMockUser
	@Test
	void givenValidId_whenCancelAppointment_thenReturnUpdatedAppointment() throws Exception {
		long appointmentId = 1L;

		var updatedAppointment = new AppointmentResponseDto(
				appointmentId,
				defaultBarber.id(),
				10L,
				appointmentTime,
				AppointmentStatus.CANCELLED_BY_CUSTOMER
		);

		when(appointmentService.cancelAppointmentByCustomer(appointmentId))
				.thenReturn(updatedAppointment);

		when(appointmentService.findById(appointmentId)).thenReturn(updatedAppointment);

		mockMvc.perform(patch("/api/appointment/{id}", appointmentId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(appointmentId))
				.andExpect(jsonPath("$.status").value("CANCELLED_BY_CUSTOMER"));
	}

	@WithMockUser
	@Test
	void givenBarberId_whenFindBarber_thenReturnBarber() throws Exception {
		when(barberService.findById(defaultBarber.id())).thenReturn(defaultBarber);

		mockMvc.perform(get("/api/appointment/barber/{id}", defaultBarber.id()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(defaultBarber.id()))
				.andExpect(jsonPath("$.bio").value(defaultBarber.bio()))
				.andExpect(jsonPath("$.price").value(defaultBarber.price()));
	}

	@WithMockUser
	@Test
	void whenFindAllBarbers_thenReturnList() throws Exception {
		var barbers = List.of(
				defaultBarber,
				new BarberResponseDto(3L, 11L, "Another barber", 30)
		);

		when(barberService.findAllBarbers()).thenReturn(barbers);

		mockMvc.perform(get("/api/appointment/barber"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2));
	}

	@Test
	void givenNoAuth_whenAccessEndpoints_thenUnauthorized() throws Exception {
		mockMvc.perform(get("/api/appointment/my"))
				.andExpect(status().isUnauthorized());

		mockMvc.perform(post("/api/appointment")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
                            {
                              "barberId": 2,
                              "customerId": 1,
                              "appointmentDateTime": "2030-01-01T10:00:00"
                            }
                            """))
				.andExpect(status().isUnauthorized());

		mockMvc.perform(patch("/api/appointment/1"))
				.andExpect(status().isUnauthorized());

		mockMvc.perform(get("/api/appointment/barber"))
				.andExpect(status().isUnauthorized());

		mockMvc.perform(get("/api/appointment/barber/1"))
				.andExpect(status().isUnauthorized());
	}
}
