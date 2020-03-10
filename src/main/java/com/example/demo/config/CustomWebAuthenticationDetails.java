package com.example.demo.config;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * 定义表单登录字段详情
 */
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
	private String code;

	public CustomWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
		String code = request.getParameter("code");
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}
}
