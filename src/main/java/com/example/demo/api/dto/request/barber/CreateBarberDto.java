package com.example.demo.api.dto.request.barber;

public record CreateBarberDto(
		long userId,
		String bio,
		int price,
		byte[] pfp
) {}
