package com.example.demo.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "barber_id")
	private Barber barber;

	@OneToOne
	@JoinColumn(name = "customer_id")
	private User customer;

	@Column(nullable = false)
	private LocalDateTime appointmentDateTime;

	public Appointment() {}

	public Appointment(long id, Barber barber, User customer, LocalDateTime appointmentDateTime) {
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

	public Barber getBarber() {
		return barber;
	}

	public void setBarber(Barber barber) {
		this.barber = barber;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public LocalDateTime getAppointmentDateTime() {
		return appointmentDateTime;
	}

	public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
		this.appointmentDateTime = appointmentDateTime;
	}
}
