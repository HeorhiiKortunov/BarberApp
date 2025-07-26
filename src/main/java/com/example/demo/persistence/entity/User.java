package com.example.demo.persistence.entity;

import com.example.demo.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, unique = true)
	private String username;

	@JsonIgnore
	private String password;
	private String email;
	private String phone;
	private boolean enabled;

	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
	private Appointment customerAppointment;

}
