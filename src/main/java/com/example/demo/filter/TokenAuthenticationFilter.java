package com.example.demo.filter;

import com.example.demo.token.TokenManager;
import com.example.demo.token.TokenStore;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Administrator
 */
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
	private TokenManager tokenManager;

	public TokenAuthenticationFilter(AuthenticationManager authManager, TokenManager tokenManager) {
		super(authManager);
		this.tokenManager = tokenManager;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader("Authorized");
		if (header == null) {
			chain.doFilter(req, res);
			return;
		}

		TokenStore tokenStore = (TokenStore) tokenManager.getToken(header);
		Authentication authentication = getAuthentication(tokenStore);

		if (authentication != null) {
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(TokenStore tokenStore) {
		if (tokenStore != null) {
			return new UsernamePasswordAuthenticationToken(tokenStore.getPrincipal(), tokenStore.getCredentials(),tokenStore.getAuthorities());
		}
		return null;
	}

}
