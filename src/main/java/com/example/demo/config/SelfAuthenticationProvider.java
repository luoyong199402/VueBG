package com.example.demo.config;

import com.example.demo.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
public class SelfAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private VueUserDetailsServiceImpl userDetailsService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private CodeService codeService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		CustomWebAuthenticationDetails customWebAuthenticationDetails = (CustomWebAuthenticationDetails) authentication.getDetails();
		// 验证码
		String code = customWebAuthenticationDetails.getCode();
		//表单输入的用户名
		String username = (String) authentication.getPrincipal();
		//表单输入的密码
		String password = (String) authentication.getCredentials();

		// 校验验证码
		if (!codeService.validateCode(username, code)) {
			throw new BadCredentialsException("验证码校验失败！");
		}

		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		//对加密密码进行验证
		if (bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
			return new UsernamePasswordAuthenticationToken(userDetails, password, null);
		} else {
			throw new BadCredentialsException("密码错误");
		}
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
