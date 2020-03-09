package com.example.demo.config;

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

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		//表单输入的用户名
		String username = (String) authentication.getPrincipal();
		//表单输入的密码
		String password = (String) authentication.getCredentials();
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		//对加密密码进行验证
		if (bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
			return new UsernamePasswordAuthenticationToken(username, password, null);
		} else {
			throw new BadCredentialsException("密码错误");
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return false;
	}
}
