package com.example.demo.enumeration;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 验证码作用域
 */
@Getter
public enum  VerificationCodeScopeEnum {

    /**
     * 邮件
     */
    LOGIN("1", "注册"),

    /**
     * 短信
     */
    REGISTER("2", "登录");

    private String value;
    private String description;

    VerificationCodeScopeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * 通过code转换为枚举
     * @param value 编码
     * @return 枚举信息
     */
    public static VerificationCodeScopeEnum fromCode(String value) {
        List<VerificationCodeScopeEnum> verificationCodeScopeEnums = Arrays.stream(VerificationCodeScopeEnum.values())
                .filter(verificationCodeScopeEnum -> StringUtils.equals(value, verificationCodeScopeEnum.value))
                .collect(Collectors.toList());

        if (verificationCodeScopeEnums != null && verificationCodeScopeEnums.size() > 0) {
            return verificationCodeScopeEnums.get(0);
        }
        return null;
    }

    @Converter(autoApply = true)
    public static class VerificationCodeScopeEnumConverter implements AttributeConverter<VerificationCodeScopeEnum, String> {
        @Override
        public String convertToDatabaseColumn(VerificationCodeScopeEnum attribute) {
            return attribute.getValue();
        }

        @Override
        public VerificationCodeScopeEnum convertToEntityAttribute(String dbData) {
            return VerificationCodeScopeEnum.fromCode(dbData);
        }
    }

}
