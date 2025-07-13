package com.example.demo.service.impl;

import com.example.demo.api.dto.request.barber.CreateBarberDto;
import com.example.demo.api.dto.request.barber.UpdateBarberDto;
import com.example.demo.api.dto.response.barber.BarberResponseDto;
import com.example.demo.mapper.BarberMapper;
import com.example.demo.persistence.entity.Barber;
import com.example.demo.persistence.entity.User;
import com.example.demo.persistence.repository.BarberRepository;
import com.example.demo.persistence.repository.UserRepository;
import com.example.demo.service.BarberService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BarberServiceImpl implements BarberService {

	private final BarberRepository barberRepository;
	private final UserRepository userRepository;
	private final BarberMapper barberMapper;

	public BarberServiceImpl(BarberMapper barberMapper, BarberRepository barberRepository, UserRepository userRepository) {
		this.barberMapper = barberMapper;
		this.barberRepository = barberRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<BarberResponseDto> findAllBarbers() {
		return barberRepository.findAll()
				.stream()
				.map(barberMapper::toResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<BarberResponseDto> findById(long id) {
		return barberRepository.findById(id).map(barberMapper::toResponseDto);
	}

	@Override
	public BarberResponseDto updateBarber(long id, UpdateBarberDto dto) {
		Barber barber = barberRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Barber not found"));

		if(dto.getBio() != null) barber.setBio(dto.getBio());
		if(dto.getPrice() != null) barber.setPrice(dto.getPrice());
		barberRepository.save(barber);

		return barberMapper.toResponseDto(barber);
	}

	@Override
	public BarberResponseDto createBarber(CreateBarberDto dto) {
		User user = userRepository.findById(dto.userId())
				.orElseThrow(() -> new RuntimeException("User not found with id " + dto.userId()));

		Barber barber = new Barber();
		barber.setUser(user);
		barber.setBio(dto.bio());
		barber.setPrice(dto.price());
		barber.setBarberAppointments(new ArrayList<>());

		Barber savedBarber = barberRepository.save(barber);

		return barberMapper.toResponseDto(savedBarber);
	}

	@Override
	public void deleteById(long id) {
		barberRepository.deleteById(id);
	}
}
