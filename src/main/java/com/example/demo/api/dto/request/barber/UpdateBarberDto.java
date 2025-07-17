package com.example.demo.api.dto.request.barber;

public class UpdateBarberDto {
	private String bio;
	private Integer price;

	public UpdateBarberDto() {}

	public UpdateBarberDto(String bio, Integer price) {
		this.bio = bio;
		this.price = price;
	}

	public String getBio() {
		return bio;
	}

	public Integer getPrice() {
		return price;
	}

}
