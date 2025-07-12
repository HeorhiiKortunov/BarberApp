package com.example.demo.api.dto.response.user;

public class UserResponseDto {

	private String username;
	private String email;
	private String phone;
	private Boolean enable;

	public UserResponseDto() {}

	public UserResponseDto(String username, String email, String phone, Boolean enable) {
		this.username = username;
		this.email = email;
		this.phone = phone;
		this.enable = enable;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
}
