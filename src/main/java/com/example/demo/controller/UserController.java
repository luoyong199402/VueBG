package com.example.demo.controller;

import com.example.demo.config.VueUser;
import com.example.demo.entity.ao.RegisterUserAO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@PostMapping
	public VueUser registerUser(@RequestBody RegisterUserAO registerUserAO) {

		return null;
	}
}
