package com.example.demo.service;

import com.example.demo.entity.ao.RegisterUserAO;
import com.example.demo.entity.dto.UserDTO;

public interface UserService {
	RegisterUserAO registerUser(RegisterUserAO registerUserAO);

	UserDTO getUserByName(String username);
}
