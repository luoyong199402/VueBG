package com.example.demo.config.security.authentication;

import com.example.demo.config.security.authentication.VueUser;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.dos.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Administrator
 */
@Component
public class VueUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserDO> optionalUserDO = userDao.findByUsername(username);
		if (optionalUserDO.isPresent()) {
			return generateVueUserByUserDO(optionalUserDO.get());
		} else {
			throw new UsernameNotFoundException("根据用户名未找到用户信息!");
		}
	}

	private VueUser generateVueUserByUserDO(UserDO userDO) {
		return VueUser.builder()
				.id(userDO.getId())
				.username(userDO.getUsername())
				.password(userDO.getPassword())
				.accountNonExpired(userDO.getAccountNonExpired())
				.accountNonLocked(userDO.getAccountNonLocked())
				.enabled(userDO.getEnabled())
				.credentialsNonExpired(true).build();
	}
}
