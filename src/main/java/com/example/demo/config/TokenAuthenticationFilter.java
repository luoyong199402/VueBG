package com.example.demo.config;

import org.springframework.security.authentication.AuthenticationManager;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Administrator
 */
public class TokenAuthenticationFilter implements Filter {
	private AuthenticationManager authenticationManager;

	public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);
	}
}
