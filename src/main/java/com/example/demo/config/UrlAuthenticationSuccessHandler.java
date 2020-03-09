package com.example.demo.config;

import com.example.demo.common.HttpResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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

	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
		//security在分布式环境token使用，下一章会写道 token存储
//		Cookie token = TokenUtils.createToken(httpServletRequest);
//		httpServletResponse.addCookie(token);
		httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
		httpServletResponse.setStatus(200);
		PrintWriter writer = httpServletResponse.getWriter();
		writer.write(objectMapper.writeValueAsString(new HttpResult(200,"登陆成功", null)));
		writer.flush();
		writer.close();
	}
}
