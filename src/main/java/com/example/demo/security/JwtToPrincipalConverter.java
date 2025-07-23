package com.example.demo.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class JwtToPrincipalConverter {
	public UserPrincipal convert(DecodedJWT jwt){
		return UserPrincipal.builder()
				.userId(Long.parseLong(jwt.getSubject()))
				.username(jwt.getClaim("email").asString())
				.authorities(extractAuthoritiesFromClaim(jwt))
				.build();

	}

	private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt){
		var claim = jwt.getClaim("authorities");
		if (claim.isNull() || claim.isMissing()) return List.of();
		return claim.asList(SimpleGrantedAuthority.class);
	}
}
