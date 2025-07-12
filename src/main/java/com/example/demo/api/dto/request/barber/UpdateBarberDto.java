package com.example.demo.api.dto.request.barber;

public class UpdateBarberDto {
	private String bio;
	private Integer price;
	private byte[] pfp;

	public UpdateBarberDto() {}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public byte[] getPfp() {
		return pfp;
	}

	public void setPfp(byte[] pfp) {
		this.pfp = pfp;
	}
}
