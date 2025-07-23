package com.example.demo.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "barbers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
