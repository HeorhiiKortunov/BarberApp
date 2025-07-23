package com.example.demo.api.dto.request.barber;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateBarberDto {
	private String bio;
	private Integer price;
}
