package com.example.demo.filter;

import com.example.demo.config.security.authentication.CustomAuthenticationToken;
import com.example.demo.config.security.authentication.LoginInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class TokenLoginFilter extends AbstractAuthenticationProcessingFilter {

	public TokenLoginFilter(AuthenticationManager authenticationManager) {
		super(new AntPathRequestMatcher("/api/login", "POST"));
		this.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
		String content = IOUtils.toString(request.getInputStream(), "utf-8");
		ObjectMapper objectMapper = new ObjectMapper();
		LoginInfo loginInfo = objectMapper.readValue(content, LoginInfo.class);

		CustomAuthenticationToken authRequest = new CustomAuthenticationToken(loginInfo);
		this.setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}

	protected void setDetails(HttpServletRequest request, CustomAuthenticationToken authRequest) {
		authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
	}
}
