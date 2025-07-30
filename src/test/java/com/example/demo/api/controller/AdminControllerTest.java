package com.example.demo.api.controller;

import com.example.demo.api.dto.request.barber.CreateBarberDto;
import com.example.demo.api.dto.request.barber.UpdateBarberDto;
import com.example.demo.api.dto.request.user.UpdateUserDto;
import com.example.demo.api.dto.response.barber.BarberResponseDto;
import com.example.demo.api.dto.response.user.UserResponseDto;
import com.example.demo.enums.Role;
import com.example.demo.security.WebSecurityConfig;
import com.example.demo.service.BarberService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(WebSecurityConfig.class)
@AutoConfigureMockMvc
class AdminControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UserService userService;

	@MockitoBean
	private BarberService barberService;

	// tests for user endpoints

	@WithMockUser(userId = 1L, username = "Admin", roles = {Role.ROLE_ADMIN})
	@Test
	void givenValidId_whenFindUser_thenReturnUser() throws Exception {
		var user = new UserResponseDto(
				1L, "John", "john@example.com", "1234567890", true, Role.ROLE_USER
		);

		when(userService.findById(1L)).thenReturn(user);

		mockMvc.perform(get("/api/admin/user/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.username").value("John"))
				.andExpect(jsonPath("$.email").value("john@example.com"))
				.andExpect(jsonPath("$.phone").value("1234567890"))
				.andExpect(jsonPath("$.enabled").value(true))
				.andExpect(jsonPath("$.role").value("ROLE_USER"));
	}

	@WithMockUser(userId = 1L, username = "Admin", roles = {Role.ROLE_ADMIN})
	@Test
	void givenUsers_whenFindAllUsers_thenReturnList() throws Exception {
		var users = List.of(
				new UserResponseDto(1L, "John", "john@example.com", "123", true, Role.ROLE_USER),
				new UserResponseDto(2L, "Mike", "mike@example.com", "456", false, Role.ROLE_BARBER)
		);

		when(userService.findAllUsers()).thenReturn(users);

		mockMvc.perform(get("/api/admin/user"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2));
	}

	@WithMockUser(userId = 1L, username = "Admin", roles = {Role.ROLE_ADMIN})
	@Test
	void givenUpdateUserDto_whenPatchUser_thenReturnUpdatedUser() throws Exception {
		String updateJson = """
            {
              "email": "new@example.com",
              "phone": "999999999",
              "enabled": true,
              "role": "ROLE_USER"
            }
        """;

		var updatedUser = new UserResponseDto(
				1L, "John", "new@example.com", "999999999", true, Role.ROLE_USER
		);

		when(userService.updateUser(anyLong(), any(UpdateUserDto.class))).thenReturn(updatedUser);

		mockMvc.perform(patch("/api/admin/user/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(updateJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value("new@example.com"))
				.andExpect(jsonPath("$.phone").value("999999999"));
	}

	@WithMockUser(userId = 1L, username = "Admin", roles = {Role.ROLE_ADMIN})
	@Test
	void givenRole_whenUpdateUserRole_thenReturnUpdatedUser() throws Exception {
		var updatedUser = new UserResponseDto(
				1L, "John", "john@example.com", "1234567890", true, Role.ROLE_BARBER
		);

		when(userService.updateUserRole(anyLong(), any(Role.class))).thenReturn(updatedUser);

		mockMvc.perform(put("/api/admin/admin/users/1/role")
						.contentType(MediaType.APPLICATION_JSON)
						.content("\"ROLE_BARBER\""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.role").value("ROLE_BARBER"));
	}

	@WithMockUser(userId = 1L, username = "Admin", roles = {Role.ROLE_ADMIN})
	@Test
	void givenValidId_whenDeleteUser_thenNoContent() throws Exception {
		doNothing().when(userService).deleteById(1L);

		mockMvc.perform(delete("/api/admin/user/1"))
				.andExpect(status().isNoContent());
	}

	// tests for barber endpoints

	@WithMockUser(userId = 1L, username = "Admin", roles = {Role.ROLE_ADMIN})
	@Test
	void givenCreateBarberDto_whenCreateBarber_thenReturnCreated() throws Exception {
		String createJson = """
            {
              "userId": 10,
              "bio": "Skilled barber",
              "price": 20
            }
        """;

		var createdBarber = new BarberResponseDto(1L, 10L, "Skilled barber", 20);

		when(barberService.createBarber(any(CreateBarberDto.class))).thenReturn(createdBarber);
		when(userService.updateUserRole(10L, Role.ROLE_BARBER)).thenReturn(
				new UserResponseDto(10L, "Mike", "mike@example.com", "123", true, Role.ROLE_BARBER)
		);

		mockMvc.perform(post("/api/admin/barber")
						.contentType(MediaType.APPLICATION_JSON)
						.content(createJson))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", org.hamcrest.Matchers.containsString("/api/admin/barber/1")))
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.userId").value(10L))
				.andExpect(jsonPath("$.bio").value("Skilled barber"))
				.andExpect(jsonPath("$.price").value(20));
	}

	@WithMockUser(userId = 1L, username = "Admin", roles = {Role.ROLE_ADMIN})
	@Test
	void givenUpdateBarberDto_whenUpdateBarber_thenReturnUpdatedBarber() throws Exception {
		String updateJson = """
            {
              "bio": "Master barber",
              "price": 30
            }
        """;

		var updatedBarber = new BarberResponseDto(1L, 10L, "Master barber", 30);

		when(barberService.updateBarber(anyLong(), any(UpdateBarberDto.class))).thenReturn(updatedBarber);

		mockMvc.perform(patch("/api/admin/barber/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(updateJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.bio").value("Master barber"))
				.andExpect(jsonPath("$.price").value(30));
	}

	@WithMockUser(userId = 1L, username = "Admin", roles = {Role.ROLE_ADMIN})
	@Test
	void givenValidId_whenDeleteBarber_thenNoContent() throws Exception {
		doNothing().when(barberService).deleteById(1L);

		mockMvc.perform(delete("/api/admin/barber/1"))
				.andExpect(status().isNoContent());
	}

	@WithMockUser(userId = 10L, username = "Mike", roles = {Role.ROLE_USER})
	@Test
	void givenUserRole_whenAccessAdminEndpoints_thenForbidden() throws Exception {
		mockMvc.perform(get("/api/admin/user/1"))
				.andExpect(status().isForbidden());

		mockMvc.perform(post("/api/admin/barber")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
                {
                  "userId": 10,
                  "bio": "Test",
                  "price": 10
                }
                """))
				.andExpect(status().isForbidden());

		mockMvc.perform(delete("/api/admin/user/1"))
				.andExpect(status().isForbidden());
	}

	@Test
	void givenNoAuth_whenAccessAdminEndpoints_thenUnauthorized() throws Exception {
		mockMvc.perform(get("/api/admin/user/1"))
				.andExpect(status().isUnauthorized());

		mockMvc.perform(post("/api/admin/barber")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
                {
                  "userId": 10,
                  "bio": "Test",
                  "price": 10
                }
                """))
				.andExpect(status().isUnauthorized());

		mockMvc.perform(delete("/api/admin/user/1"))
				.andExpect(status().isUnauthorized());
	}

}
