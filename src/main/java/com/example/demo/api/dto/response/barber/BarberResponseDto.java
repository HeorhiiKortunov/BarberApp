package com.example.demo.api.dto.response.barber;

public record BarberResponseDto(
		long id,
		long userId,
		String bio,
		int price,
		byte[] pfp
) {}