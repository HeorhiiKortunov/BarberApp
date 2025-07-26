package com.example.demo.api.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateUserDto {
	private String email;
	private String phone;
	private Boolean enabled;
	private String role;

}
