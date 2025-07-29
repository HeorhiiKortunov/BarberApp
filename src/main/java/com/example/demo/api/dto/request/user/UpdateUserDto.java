package com.example.demo.api.dto.request.user;

import com.example.demo.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateUserDto {

	@Email(message = "Email must be valid")
	private String email;

	@Pattern(regexp = "\\d{10,15}", message = "Phone number must be 10 to 15 digits")
	private String phone;

	private Boolean enabled;

	private Role role;
}
