package com.example.demo.api.dto.request.barber;

import com.example.demo.persistence.entity.User;

public record CreateBarberDto(
		User user,
		String bio,
		int price
) {}
