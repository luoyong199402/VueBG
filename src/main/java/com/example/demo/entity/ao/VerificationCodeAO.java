package com.example.demo.entity.ao;

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
	private String type;
	private int expirationDate;
	private String data;
}
