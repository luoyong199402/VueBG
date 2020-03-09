package com.example.demo.controller;

import com.example.demo.entity.EmailEntity;
import com.example.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
public class MailController {
	@Autowired
	private EmailService emailService;

	@PostMapping
	public void sendEmail(@RequestBody EmailEntity emailEntity) throws InterruptedException {
		emailService.send(emailEntity);
	}

}
