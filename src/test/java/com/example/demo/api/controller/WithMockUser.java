package com.example.demo.api.controller;

import com.example.demo.enums.Role;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockUserSecurityContextFactory.class)
public @interface WithMockUser {
	long userId() default 1L;
	String username() default "John";
	Role[] roles() default { Role.ROLE_USER };
}
