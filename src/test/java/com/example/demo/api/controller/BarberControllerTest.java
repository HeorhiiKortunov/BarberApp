package com.example.demo.api.controller;

import com.example.demo.api.dto.request.barber.UpdateBarberDto;
import com.example.demo.api.dto.response.barber.BarberResponseDto;
import com.example.demo.enums.Role;
import com.example.demo.security.WebSecurityConfig;
import com.example.demo.service.BarberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(WebSecurityConfig.class)
@AutoConfigureMockMvc
class BarberControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private BarberService barberService;

	@Test
	void givenNoAuth_whenGetMyProfile_thenUnauthorized() throws Exception {
		mockMvc.perform(get("/api/barber/me"))
				.andExpect(status().isUnauthorized());
	}

	@WithMockUser(userId = 10L, username = "Mike", roles = {Role.ROLE_BARBER})
	@Test
	void givenAuth_whenGetMyProfile_thenReturnBarber() throws Exception {
		var barberResponse = new BarberResponseDto(
				1L,      // barber id
				10L,     // user id matches @WithMockUser
				"Experienced barber",
				25
		);

		when(barberService.findByUserId(eq(10L))).thenReturn(barberResponse);
		when(barberService.findById(eq(1L))).thenReturn(barberResponse);

		mockMvc.perform(get("/api/barber/me"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.userId").value(10L))
				.andExpect(jsonPath("$.bio").value("Experienced barber"))
				.andExpect(jsonPath("$.price").value(25));
	}

	@WithMockUser(userId = 10L, username = "Mike", roles = {Role.ROLE_BARBER})
	@Test
	void givenAuth_whenUpdateMyProfile_thenReturnUpdatedBarber() throws Exception {
		String updateJson = """
            {
              "bio": "Master barber",
              "price": 30
            }
        """;

		var updatedBarber = new BarberResponseDto(
				1L,
				10L,
				"Master barber",
				30
		);

		when(barberService.findByUserId(eq(10L))).thenReturn(updatedBarber);
		when(barberService.updateBarber(eq(1L), any(UpdateBarberDto.class)))
				.thenReturn(updatedBarber);

		mockMvc.perform(put("/api/barber/me")
						.contentType(MediaType.APPLICATION_JSON)
						.content(updateJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.userId").value(10L))
				.andExpect(jsonPath("$.bio").value("Master barber"))
				.andExpect(jsonPath("$.price").value(30));
	}

	@WithMockUser(userId = 10L, username = "Mike", roles = {Role.ROLE_BARBER})
	@Test
	void givenInvalidData_whenUpdateMyProfile_thenBadRequest() throws Exception {
		String invalidJson = """
            {
              "bio": "",
              "price": -10
            }
        """;

		var updatedBarber = new BarberResponseDto(
				1L,
				10L,
				"",
				-10
		);

		when(barberService.findByUserId(eq(10L))).thenReturn(updatedBarber);

		mockMvc.perform(put("/api/barber/me")
						.contentType(MediaType.APPLICATION_JSON)
						.content(invalidJson))
				.andExpect(status().isBadRequest());
	}
}
