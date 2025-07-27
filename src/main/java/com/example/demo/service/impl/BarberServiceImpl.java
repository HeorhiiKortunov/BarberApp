package com.example.demo.service.impl;

import com.example.demo.api.dto.request.barber.CreateBarberDto;
import com.example.demo.api.dto.request.barber.UpdateBarberDto;
import com.example.demo.api.dto.response.barber.BarberResponseDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.BarberMapper;
import com.example.demo.persistence.entity.Barber;
import com.example.demo.persistence.repository.BarberRepository;
import com.example.demo.persistence.repository.UserRepository;
import com.example.demo.service.BarberService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BarberServiceImpl implements BarberService {

	private final BarberRepository barberRepository;
	private final UserRepository userRepository;
	private final BarberMapper barberMapper;


	@Override
	public List<BarberResponseDto> findAllBarbers() {
		return barberRepository.findAll()
				.stream()
				.map(barberMapper::toResponseDto)
				.toList();
	}

	@Override
	public BarberResponseDto findById(long id) {
		return barberRepository.findById(id).map(barberMapper::toResponseDto)
				.orElseThrow(() -> new ResourceNotFoundException("Barber not found"));
	}

	@Override
	public BarberResponseDto updateBarber(long id, UpdateBarberDto dto) {
		var barber = barberRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Barber not found"));

		barberMapper.updateBarberFromDto(barber, dto);
		var savedBarber = barberRepository.save(barber);

		return barberMapper.toResponseDto(savedBarber);
	}

	@Override
	public BarberResponseDto createBarber(CreateBarberDto dto) {
		var barber = barberMapper.fromCreateDto(dto);
		var savedBarber = barberRepository.save(barber);
		return barberMapper.toResponseDto(savedBarber);
	}

	@Override
	public BarberResponseDto findByUserId(long id) {
		return barberRepository.findByUserId(id).map(barberMapper::toResponseDto)
				.orElseThrow(() -> new ResourceNotFoundException("Barber not found"));
	}

	@Override
	public void deleteById(long id) {
		barberRepository.deleteById(id);
	}
}
