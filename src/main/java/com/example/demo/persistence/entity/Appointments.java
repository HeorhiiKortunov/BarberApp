package com.example.demo.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "barber_id")
	private Barbers barber;

	@OneToOne
	@JoinColumn(name = "customer_id")
	private Users customer;

	@Column(nullable = false)
	private LocalDateTime appointmentDateTime;

	public Appointments() {}

	public Appointments(long id, Barbers barber, Users customer, LocalDateTime appointmentDateTime) {
		this.id = id;
		this.barber = barber;
		this.customer = customer;
		this.appointmentDateTime = appointmentDateTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Barbers getBarber() {
		return barber;
	}

	public void setBarber(Barbers barber) {
		this.barber = barber;
	}

	public Users getCustomer() {
		return customer;
	}

	public void setCustomer(Users customer) {
		this.customer = customer;
	}

	public LocalDateTime getAppointmentDateTime() {
		return appointmentDateTime;
	}

	public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
		this.appointmentDateTime = appointmentDateTime;
	}
}
