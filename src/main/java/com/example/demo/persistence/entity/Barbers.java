package com.example.demo.persistence.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "barbers")
public class Barbers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private long userId;

	@OneToMany(mappedBy = "barber", cascade = CascadeType.ALL)
	private List<Appointments> barberAppointments = new ArrayList<>();

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] pfp;

	private String bio;
	private int price;

	public Barbers() {}

	public Barbers(long id, long userId, List<Appointments> barberAppointments, byte[] pfp, String bio, int price) {
		this.id = id;
		this.userId = userId;
		this.barberAppointments = barberAppointments;
		this.pfp = pfp;
		this.bio = bio;
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public List<Appointments> getBarberAppointments() {
		return barberAppointments;
	}

	public void setBarberAppointments(List<Appointments> barberAppointments) {
		this.barberAppointments = barberAppointments;
	}

	public byte[] getPfp() {
		return pfp;
	}

	public void setPfp(byte[] pfp) {
		this.pfp = pfp;
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
