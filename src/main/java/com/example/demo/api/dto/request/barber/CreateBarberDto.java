package com.example.demo.api.dto.request.barber;

public record CreateBarberDto(
		long userId,
		byte[] pfp,
		String bio,
		int price
)
{}
