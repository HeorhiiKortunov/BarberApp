package com.example.demo.mapper;

import com.example.demo.api.dto.request.barber.CreateBarberDto;
import com.example.demo.api.dto.request.barber.UpdateBarberDto;
import com.example.demo.api.dto.response.barber.BarberResponseDto;
import com.example.demo.persistence.entity.Barber;
import com.example.demo.persistence.entity.User;
import com.example.demo.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BarberMapper {

	private final UserRepository userRepository;


	public BarberMapper(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Barber fromCreateDto(CreateBarberDto dto){
		Barber barber = new Barber();
		User user = userRepository.findById(dto.userId()).orElseThrow(() -> new RuntimeException("User not found"));

		barber.setUser(user);
		barber.setBio(dto.bio());
		barber.setPrice(dto.price());
		return barber;
	}

	public BarberResponseDto toResponseDto(Barber barber){
		return new BarberResponseDto(
				barber.getId(),
				barber.getUser().getId(),
				barber.getBio(),
				barber.getPrice()
		);
	}

	public void updateBarberFromDto(Barber barber, UpdateBarberDto dto){
		if(dto.getBio() != null) barber.setBio(dto.getBio());
		if(dto.getPrice() != null) barber.setPrice(dto.getPrice());
	}
}
