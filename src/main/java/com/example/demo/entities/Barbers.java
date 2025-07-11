package com.example.demo.entities;

import jakarta.persistence.*;

public class Barbers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private long userId;

	private String bio;
	private int price;

	//TODO: add pfp
}
