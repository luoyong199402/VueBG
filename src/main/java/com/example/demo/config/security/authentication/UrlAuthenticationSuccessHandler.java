package com.example.demo.config.security.authentication;

import com.example.demo.common.HttpResult;
import com.example.demo.token.RedisTokenManager;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
				.principal(authentication.getPrincipal())
				.createTime(new Date())
				.expirationDate(RedisTokenManager.getTokenExpireTime()).build();

		String token = tokenManager.createToken(tokenStore);
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("token", token);
		dataMap.put("expirationDate", RedisTokenManager.getTokenExpireTime());
		dataMap.put("createTime", tokenStore.getCreateTime());

		httpServletResponse.setStatus(200);
		PrintWriter writer = httpServletResponse.getWriter();
		writer.write(objectMapper.writeValueAsString(new HttpResult(200,"登陆成功", dataMap)));
		writer.flush();
		writer.close();
	}
}
