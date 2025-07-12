package com.example.demo.persistence.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "barbers")
public class Barber {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "barber", cascade = CascadeType.ALL)
	private List<Appointment> barberAppointments = new ArrayList<>();

	private String bio;
	private int price;

	public Barber() {}

	public Barber(long id, User user, List<Appointment> barberAppointments, String bio, int price) {
		this.id = id;
		this.user = user;
		this.barberAppointments = barberAppointments;
		this.bio = bio;
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Appointment> getBarberAppointments() {
		return barberAppointments;
	}

	public void setBarberAppointments(List<Appointment> barberAppointments) {
		this.barberAppointments = barberAppointments;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
