package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtIssuer {
	private final JwtProperties properties;

	public JwtIssuer(JwtProperties properties) {
		this.properties = properties;
	}

	public String issue(long userId, String email, String role){
		return JWT.create()
				.withSubject(String.valueOf(userId))
				.withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
				.withClaim("e", email)
				.withClaim("r", role)
				.sign(Algorithm.HMAC256(properties.getSecretKey()));
	}
}
