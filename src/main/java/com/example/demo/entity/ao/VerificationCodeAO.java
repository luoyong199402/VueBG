package com.example.demo.entity.ao;

import com.example.demo.enumeration.VerificationCodeEnum;
import com.example.demo.enumeration.VerificationCodeScopeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationCodeAO {
	private String businessKey;
	private String code;
	private VerificationCodeEnum type;
	private int expirationDate;
	private String data;
	private VerificationCodeScopeEnum scope;
}
