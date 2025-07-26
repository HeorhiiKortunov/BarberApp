package com.example.demo.api.dto.request.user;

import com.example.demo.enums.Role;
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
	private Role role;

}
