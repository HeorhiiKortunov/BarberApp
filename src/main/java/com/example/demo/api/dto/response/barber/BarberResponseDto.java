package com.example.demo.api.dto.response.barber;

import com.example.demo.persistence.entity.User;

public record BarberResponseDto(
		long id,
		User user,
		String bio,
		int price
) {}