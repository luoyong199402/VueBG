package com.example.demo.config.security.authentication;

import com.example.demo.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
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
		LoginInfo loginInfo = (LoginInfo) authentication.getPrincipal();

		// 校验验证码
		if (!codeService.validateCode(loginInfo.getUsername(), loginInfo.getCode())) {
			throw new BadCredentialsException("验证码校验失败！");
		}
		codeService.clearCode(loginInfo.getUsername());

		UserDetails userDetails = userDetailsService.loadUserByUsername(loginInfo.getUsername());
		//对加密密码进行验证
		if (bCryptPasswordEncoder.matches(loginInfo.getPassword(), userDetails.getPassword())) {
			return new CustomAuthenticationToken(userDetails, null, null);
		} else {
			throw new BadCredentialsException("密码错误");
		}
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return true;
	}
}
