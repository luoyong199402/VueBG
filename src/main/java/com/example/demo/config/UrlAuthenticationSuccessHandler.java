package com.example.demo.config;

import com.example.demo.common.HttpResult;
import com.example.demo.token.TokenManager;
import com.example.demo.token.TokenStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ly
 */
@Component
public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TokenManager tokenManager;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
		TokenStore tokenStore = TokenStore.builder()
				.authorities(authentication.getAuthorities())
				.credentials(authentication.getCredentials())
				.isAuthenticated(authentication.isAuthenticated())
				.principal(authentication.getPrincipal()).build();

		String token = tokenManager.createToken(tokenStore);

		httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
		httpServletResponse.setStatus(200);
		PrintWriter writer = httpServletResponse.getWriter();
		writer.write(objectMapper.writeValueAsString(new HttpResult(200,"登陆成功", null)));
		writer.flush();
		writer.close();
	}
}
