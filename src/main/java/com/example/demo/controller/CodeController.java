package com.example.demo.controller;

import com.example.demo.entity.ao.VerificationCodeAO;
import com.example.demo.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/code")
public class CodeController {

	@Autowired
	private CodeService codeService;

	@PostMapping
	public VerificationCodeAO addCode(@RequestBody VerificationCodeAO verificationCodeAO) {
		return codeService.addCode(verificationCodeAO);
	}
}
