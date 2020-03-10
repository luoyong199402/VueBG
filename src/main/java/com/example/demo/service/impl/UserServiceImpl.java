package com.example.demo.service.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.ao.RegisterUserAO;
import com.example.demo.entity.ao.VerificationCodeAO;
import com.example.demo.entity.dos.UserDO;
import com.example.demo.entity.dto.UserDTO;
import com.example.demo.service.CodeService;
import com.example.demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private CodeService codeService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public RegisterUserAO registerUser(RegisterUserAO registerUserAO) {
		// 校验验证码
		if (!validateCode(registerUserAO.getUsername(), registerUserAO.getCode())) {
			codeService.clearCode(registerUserAO.getUsername());
			throw new RuntimeException("验证码输入错误。 请重新申请！");
		}

		// 校验用户是否存在
		if (validateUserExist(registerUserAO.getUsername())) {
			throw new RuntimeException("用户已存在");
		}

		//  生成用户信息并加密
		UserDO userDO = generateUserDOByRegisterUserAO(registerUserAO);
		userDao.save(userDO);

		return registerUserAO;
	}

	@Override
	public UserDTO getUserByName(String username) {
		Optional<UserDO> optionalUserDO = userDao.findByUsername(username);
		optionalUserDO.orElseThrow(() -> new RuntimeException("用户不存在！"));
		return generateUserDTOByUserDO(optionalUserDO.get());
	}

	private boolean validateCode(String username, String code) {
		VerificationCodeAO validateCode = codeService.getCode(username);
		return StringUtils.equalsIgnoreCase(code, validateCode.getCode());
	}

	private boolean validateUserExist(String username) {
		Optional<UserDO> optionalUserDO = userDao.findByUsername(username);
		return optionalUserDO.isPresent();
	}

	private UserDO generateUserDOByRegisterUserAO(RegisterUserAO registerUserAO) {
		return UserDO.builder()
				.username(registerUserAO.getUsername())
				.password(bCryptPasswordEncoder.encode(registerUserAO.getPassword()))
				.accountNonExpired(false)
				.accountNonLocked(false)
				.enabled(true).build();
	}

	private UserDTO generateUserDTOByUserDO(UserDO userDO) {
		return UserDTO.builder()
				.id(userDO.getId())
				.username(userDO.getUsername())
				.password(userDO.getPassword()).build();
	}
}
