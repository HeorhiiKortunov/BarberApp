package com.example.demo.security;

import com.example.demo.persistence.entity.Authority;
import com.example.demo.persistence.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		var roles = user.getAuthorities().stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.getAuthority()))
				.toList();

		return UserPrincipal.builder()
				.userId(user.getId())
				.username(user.getUsername())
				.authorities(roles)
				.password(user.getPassword())
				.build();
	}
}
