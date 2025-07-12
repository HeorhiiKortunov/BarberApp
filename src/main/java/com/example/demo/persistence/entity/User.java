package com.example.demo.persistence.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, unique = true)
	private String username;

	private String password;
	private String email;
	private String phone;
	private boolean enabled;
	private String role;

	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Appointment> customerAppointments = new ArrayList<>();

	public User() {}

	public User(long id, String username, String password, String email, String phone, boolean enabled, String role, List<Appointment> customerAppointments) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.enabled = enabled;
		this.role = role;
		this.customerAppointments = customerAppointments;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Appointment> getCustomerAppointments() {
		return customerAppointments;
	}

	public void setCustomerAppointments(List<Appointment> customerAppointments) {
		this.customerAppointments = customerAppointments;
	}
}
