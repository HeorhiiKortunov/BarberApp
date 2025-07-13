package com.example.demo.api.dto.response.barber;

import com.example.demo.persistence.entity.User;

public record BarberResponseDto(
		long id,
		long userId,
		String bio,
		int price
) {}