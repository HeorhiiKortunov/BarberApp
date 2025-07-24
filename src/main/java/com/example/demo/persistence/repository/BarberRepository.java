package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.Barber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BarberRepository extends JpaRepository<Barber, Long> {
	Optional<Barber> findByUserId(Long userId);
}
