package com.example.demo.enumeration;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum VerificationCodeEnum {
	/**
	 * 邮件
	 */
	EMAIL("1", "邮件"),

	/**
	 * 短信
	 */
	MESSAGE("2", "短信");

	private String value;
	private String description;

	VerificationCodeEnum(String value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * 通过code转换为枚举
	 * @param value 编码
	 * @return 枚举信息
	 */
	public static VerificationCodeEnum fromCode(String value) {
		List<VerificationCodeEnum> sexEnumList = Arrays.stream(VerificationCodeEnum.values())
				.filter(sexEnum -> StringUtils.equals(value, sexEnum.value))
				.collect(Collectors.toList());

		if (sexEnumList != null && sexEnumList.size() > 0) {
			return sexEnumList.get(0);
		}
		return null;
	}

	@Converter(autoApply = true)
	public static class VerificationCodeEnumConverter implements AttributeConverter<VerificationCodeEnum, String> {
		@Override
		public String convertToDatabaseColumn(VerificationCodeEnum attribute) {
			return attribute.getValue();
		}

		@Override
		public VerificationCodeEnum convertToEntityAttribute(String dbData) {
			return VerificationCodeEnum.fromCode(dbData);
		}
	}
}
