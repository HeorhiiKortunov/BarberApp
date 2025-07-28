package com.example.demo.service.impl;

import com.example.demo.api.dto.response.auth.LoginResponse;
import com.example.demo.enums.Role;
import com.example.demo.security.JwtIssuer;
import com.example.demo.security.UserPrincipal;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

	@InjectMocks
	private AuthServiceImpl authService;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private JwtIssuer jwtIssuer;

	@Mock
	private Authentication authentication;

	@Mock
	private UserPrincipal userPrincipal;

	@Mock
	private SecurityContext securityContext;

	@BeforeEach
	void setUp() {
		SecurityContextHolder.setContext(securityContext);
	}

	@Test
	void givenValidCredentials_whenAttemptLogin_shouldReturnLoginResponseWithToken() {
		String username = "user";
		String password = "pass";
		String expectedToken = "jwt-token";

		when(authenticationManager.authenticate(any()))
				.thenReturn(authentication);

		when(authentication.getPrincipal()).thenReturn(userPrincipal);
		when(userPrincipal.getUserId()).thenReturn(123L);
		when(userPrincipal.getUsername()).thenReturn(username);

		SimpleGrantedAuthority roleUser = new SimpleGrantedAuthority("ROLE_USER");

		java.util.List<GrantedAuthority> authorities = new java.util.ArrayList<>();
		authorities.add(roleUser);

		doReturn(authorities).when(userPrincipal).getAuthorities();

		assertFalse(userPrincipal.getAuthorities().isEmpty());

		when(jwtIssuer.issue(anyLong(), anyString(), anyList()))
				.thenReturn(expectedToken);

		LoginResponse response = authService.attemptLogin(username, password);

		assertNotNull(response);
		assertEquals(expectedToken, response.accessToken());
	}

	@Test
	void whenAuthenticationFails_shouldThrowException() {
		String username = "user";
		String password = "wrongpass";

		when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Bad credentials"));

		assertThrows(RuntimeException.class, () -> authService.attemptLogin(username, password));
		verify(securityContext, never()).setAuthentication(any());
	}
}
