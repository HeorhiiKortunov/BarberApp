package com.example.demo.api.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserDto(

		@NotBlank(message = "Username is required")
		String username,

		@NotBlank(message = "Password is required")
		@Size(min = 6, message = "Password must be at least 6 characters long")
		String password,

		@NotBlank(message = "Email is required")
		@Email(message = "Email should be valid")
		String email,

		@NotBlank(message = "Phone number is required")
		@Pattern(regexp = "\\d{10,15}", message = "Phone number must be 10 to 15 digits")
		String phone
) {}
