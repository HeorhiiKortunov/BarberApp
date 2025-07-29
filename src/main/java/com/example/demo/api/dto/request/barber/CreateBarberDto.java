package com.example.demo.api.dto.request.barber;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record CreateBarberDto(

		Long userId,

		@Size(max = 500, message = "Bio cannot be longer than 500 characters")
		String bio,

		@Min(value = 0, message = "Price must be zero or positive")
		int price
) {}
