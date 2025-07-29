package com.example.demo.mapper;

import com.example.demo.api.dto.request.barber.CreateBarberDto;
import com.example.demo.api.dto.request.barber.UpdateBarberDto;
import com.example.demo.api.dto.response.barber.BarberResponseDto;
import com.example.demo.persistence.entity.Barber;
import com.example.demo.persistence.entity.User;
import com.example.demo.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BarberMapperTest {

	private UserRepository userRepository;
	private BarberMapper barberMapper;

	@BeforeEach
	void setUp() {
		userRepository = mock(UserRepository.class);
		barberMapper = new BarberMapper(userRepository);
	}

	@Test
	void fromCreateDto_shouldMapCorrectly() {
		long userId = 1L;
		User user = new User();
		user.setId(userId);
		CreateBarberDto dto = new CreateBarberDto(userId, "bio", 25);

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		Barber result = barberMapper.fromCreateDto(dto);

		assertEquals(user, result.getUser());
		assertEquals("bio", result.getBio());
		assertEquals(25, result.getPrice());
	}

	@Test
	void fromCreateDto_userNotFound_shouldThrow() {
		long userId = 1L;
		CreateBarberDto dto = new CreateBarberDto(userId, "bio", 25);

		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> barberMapper.fromCreateDto(dto));
	}

	@Test
	void toResponseDto_shouldMapCorrectly() {
		User user = new User();
		user.setId(1L);
		Barber barber = new Barber();
		barber.setId(2L);
		barber.setUser(user);
		barber.setBio("Skilled barber");
		barber.setPrice(25);

		BarberResponseDto dto = barberMapper.toResponseDto(barber);

		assertEquals(2L, dto.id());
		assertEquals(1L, dto.userId());
		assertEquals("Skilled barber", dto.bio());
		assertEquals(25, dto.price());
	}

	@Test
	void updateBarberFromDto_shouldUpdateOnlyNonNullFields() {
		Barber barber = new Barber();
		barber.setBio("Old Bio");
		barber.setPrice(25);

		UpdateBarberDto dto = new UpdateBarberDto("New Bio", null);

		barberMapper.updateBarberFromDto(barber, dto);

		assertEquals("New Bio", barber.getBio());
		assertEquals(25, barber.getPrice());
	}
}
