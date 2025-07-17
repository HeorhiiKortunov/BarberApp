package com.example.demo.api.dto.request.user;

public class UpdateUserDto {
	private String email;
	private String phone;
	private Boolean enabled;

	public UpdateUserDto() {}

	public UpdateUserDto(String email, String phone, Boolean enabled) {
		this.email = email;
		this.phone = phone;
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public Boolean getEnabled() {
		return enabled;
	}

}
