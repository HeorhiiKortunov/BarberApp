package com.example.demo;

import com.example.demo.persistence.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BarbersAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarbersAppApplication.class, args);
	}

}
