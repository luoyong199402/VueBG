package com.example.demo.controller.info;

import com.example.demo.entity.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/info/")
public class InfoController {
	@PostMapping
	public UserDTO addInfo(@RequestBody UserDTO userDTO) {
		return null;
	}
}
