package com.example.demo.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @author Administrator
 */
@Component
public class VueUserDetailsServiceImpl implements UserDetailsService {
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		VueUser vueUser = new VueUser();
		if(ObjectUtils.isEmpty(vueUser)){
			throw new UsernameNotFoundException("根据用户名未找到用户信息!");
		}
		return vueUser;
	}
}
