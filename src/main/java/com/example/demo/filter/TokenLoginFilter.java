package com.example.demo.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;

	public TokenLoginFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		this.setPostOnly(false);
	}
}
