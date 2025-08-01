package com.example.demo.api.controller;

import com.example.demo.security.UserPrincipal;
import com.example.demo.security.UserPrincipalAuthToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;

public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUser> {

	@Override
	public SecurityContext createSecurityContext(WithMockUser annotation) {
		var authorities = Arrays.stream(annotation.roles())
				.map(role -> new SimpleGrantedAuthority(role.name()))
				.toList();

		var principal = UserPrincipal.builder()
				.userId(annotation.userId())
				.username(annotation.username())
				.authorities(authorities)
				.build();

		var context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(new UserPrincipalAuthToken(principal));

		return context;
	}


}
