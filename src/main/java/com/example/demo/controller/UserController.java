package com.example.demo.controller;

import com.example.demo.config.VueUser;
import com.example.demo.entity.ao.RegisterUserAO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public VueUser registerUser(@RequestBody RegisterUserAO registerUserAO) {
		RegisterUserAO registerUserAO1 = userService.registerUser(registerUserAO);
		VueUser build = VueUser.builder()
				.username(registerUserAO1.getUsername()).build();
		return build;
	}

	@GetMapping
	public Object getUserInfo(Authentication authentication ) {
		return authentication;
	}
}
