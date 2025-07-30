package com.example.demo.api.dto.request.barber;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateBarberDto {

	@Size(max = 500, message = "Bio cannot be longer than 500 characters")
	private String bio;

	@PositiveOrZero
	private Integer price;
}
