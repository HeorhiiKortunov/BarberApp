package com.example.demo.service;

import com.example.demo.api.dto.request.barber.CreateBarberDto;
import com.example.demo.api.dto.request.barber.UpdateBarberDto;
import com.example.demo.api.dto.response.barber.BarberResponseDto;

import java.util.List;
import java.util.Optional;

public interface BarberService {
	List<BarberResponseDto> findAllBarbers();
	Optional<BarberResponseDto> findById(long id);
	BarberResponseDto updateBarber(long id, UpdateBarberDto dto);
	BarberResponseDto createBarber(CreateBarberDto dto);
	void deleteById(long id);
}
