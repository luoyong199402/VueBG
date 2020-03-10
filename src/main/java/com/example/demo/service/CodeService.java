package com.example.demo.service;

import com.example.demo.entity.ao.VerificationCodeAO;

public interface CodeService {
	VerificationCodeAO addCode(VerificationCodeAO verificationCodeAO);

	VerificationCodeAO getCode(String businessKey);

	void clearCode(String businessKey);

	Boolean validateCode(String businessKey, String code);
}
