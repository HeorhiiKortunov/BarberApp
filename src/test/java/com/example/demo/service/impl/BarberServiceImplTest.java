package com.example.demo.service.impl;

import com.example.demo.api.dto.request.barber.CreateBarberDto;
import com.example.demo.api.dto.request.barber.UpdateBarberDto;
import com.example.demo.api.dto.response.barber.BarberResponseDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.BarberMapper;
import com.example.demo.persistence.entity.Barber;
import com.example.demo.persistence.repository.BarberRepository;
import com.example.demo.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BarberServiceImplTest {

	@InjectMocks
	private BarberServiceImpl barberService;

	@Mock
	private BarberRepository barberRepository;

	@Mock
	private BarberMapper barberMapper;

	private Barber barber;
	private BarberResponseDto responseDto;

	@BeforeEach
	void setUp() {
		barber = new Barber();
		barber.setId(1L);

		responseDto = mock(BarberResponseDto.class);
	}

	//tests for findAllBarbers
	@Test
	void whenFindAllBarbers_thenReturnListOfResponseDtos() {
		Barber barber2 = new Barber();
		List<Barber> barbers = List.of(barber, barber2);

		when(barberRepository.findAll()).thenReturn(barbers);
		when(barberMapper.toResponseDto(any())).thenReturn(mock(BarberResponseDto.class));

		List<BarberResponseDto> result = barberService.findAllBarbers();

		assertEquals(2, result.size());
		verify(barberRepository).findAll();
		verify(barberMapper, times(2)).toResponseDto(any());
	}

	//tests for findById
	@Test
	void givenValidId_whenFindById_thenReturnResponseDto() {
		when(barberRepository.findById(1L)).thenReturn(Optional.of(barber));
		when(barberMapper.toResponseDto(barber)).thenReturn(responseDto);

		BarberResponseDto result = barberService.findById(1L);

		assertEquals(responseDto, result);
		verify(barberRepository).findById(1L);
		verify(barberMapper).toResponseDto(barber);
	}

	@Test
	void givenInvalidId_whenFindById_thenThrow() {
		when(barberRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> barberService.findById(1L));
		verifyNoInteractions(barberMapper);
	}

	//tests for updateBarber
	@Test
	void givenValidId_whenUpdateBarber_thenReturnUpdatedDto() {
		UpdateBarberDto dto = mock(UpdateBarberDto.class);
		Barber updated = new Barber();

		when(barberRepository.findById(1L)).thenReturn(Optional.of(barber));
		when(barberRepository.save(barber)).thenReturn(updated);
		when(barberMapper.toResponseDto(updated)).thenReturn(responseDto);

		BarberResponseDto result = barberService.updateBarber(1L, dto);

		assertEquals(responseDto, result);
		verify(barberMapper).updateBarberFromDto(barber, dto);
		verify(barberRepository).save(barber);
		verify(barberMapper).toResponseDto(updated);
	}

	@Test
	void givenInvalidId_whenUpdateBarber_thenThrow() {
		UpdateBarberDto dto = mock(UpdateBarberDto.class);
		when(barberRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> barberService.updateBarber(1L, dto));
		verify(barberRepository, never()).save(any());
	}

	//tests for createBarber
	@Test
	void whenCreateBarber_thenReturnResponseDto() {
		CreateBarberDto dto = mock(CreateBarberDto.class);
		when(barberMapper.fromCreateDto(dto)).thenReturn(barber);
		when(barberRepository.save(barber)).thenReturn(barber);
		when(barberMapper.toResponseDto(barber)).thenReturn(responseDto);

		BarberResponseDto result = barberService.createBarber(dto);

		assertEquals(responseDto, result);
		verify(barberMapper).fromCreateDto(dto);
		verify(barberRepository).save(barber);
		verify(barberMapper).toResponseDto(barber);
	}


	//tests for findByUserId
	@Test
	void givenValidUserId_whenFindByUserId_thenReturnResponseDto() {
		when(barberRepository.findByUserId(1L)).thenReturn(Optional.of(barber));
		when(barberMapper.toResponseDto(barber)).thenReturn(responseDto);

		BarberResponseDto result = barberService.findByUserId(1L);

		assertEquals(responseDto, result);
		verify(barberRepository).findByUserId(1L);
		verify(barberMapper).toResponseDto(barber);
	}

	@Test
	void givenInvalidUserId_whenFindByUserId_thenThrow() {
		when(barberRepository.findByUserId(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> barberService.findByUserId(1L));
		verifyNoInteractions(barberMapper);
	}

	//tests for deleteById
	@Test
	void givenId_whenDeleteById_shouldCallRepositoryDelete() {
		barberService.deleteById(1L);

		verify(barberRepository).deleteById(1L);
	}
}
