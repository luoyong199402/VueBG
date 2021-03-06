package com.example.demo.config.security.authentication;

import com.example.demo.common.HttpResult;
import com.example.demo.token.TokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Administrator
 */
@Component
public class UrlLogoutSuccessHandler implements LogoutSuccessHandler {

	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
		String header = httpServletRequest.getHeader("Authorized");
		tokenManager.removeToken(header);

		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setStatus(200);
		PrintWriter writer = httpServletResponse.getWriter();
		writer.write(objectMapper.writeValueAsString(new HttpResult(200,"登出成功", null)));
		writer.flush();
		writer.close();
	}
}
