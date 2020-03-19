package com.example.demo.config.security.authentication;

import com.example.demo.common.HttpResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Administrator
 */
@Component
public class UrlAuthenticationFailureHandler implements AuthenticationFailureHandler {
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
		e.printStackTrace();

		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setStatus(401);
		PrintWriter writer = httpServletResponse.getWriter();
		writer.write(objectMapper.writeValueAsString(new HttpResult(401,e.getMessage(), null)));
		writer.flush();
		writer.close();
	}
}
