package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	List<Appointment> findByBarberId(Long barberId);
	Optional<Appointment> findByCustomerId(Long customerId);
}
