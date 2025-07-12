package com.example.demo.mapper;

import com.example.demo.api.dto.request.barber.CreateBarberDto;
import com.example.demo.api.dto.request.barber.UpdateBarberDto;
import com.example.demo.api.dto.response.barber.BarberResponseDto;
import com.example.demo.persistence.entity.Barber;
import org.springframework.stereotype.Component;

@Component
public class BarberMapper {

	public Barber fromCreateDto(CreateBarberDto dto){
		Barber barber = new Barber();
		barber.setUser(dto.user());
		barber.setBio(dto.bio());
		barber.setPrice(dto.price());
		return barber;
	}

	public BarberResponseDto toResponseDto(Barber barber){
		return new BarberResponseDto(
				barber.getId(),
				barber.getUser(),
				barber.getBio(),
				barber.getPrice()
		);
	}

	public void updateBarberFromDto(Barber barber, UpdateBarberDto dto){
		if(dto.getBio() != null) barber.setBio(dto.getBio());
		if(dto.getPrice() != null) barber.setPrice(dto.getPrice());
	}
}
