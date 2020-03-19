package com.example.demo.service.impl;

import com.example.demo.entity.EmailEntity;
import com.example.demo.entity.ao.VerificationCodeAO;
import com.example.demo.enumeration.VerificationCodeEnum;
import com.example.demo.service.CodeService;
import com.example.demo.service.EmailService;
import com.example.demo.utils.VerifyCodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Service
public class CodeServiceImpl implements CodeService {

	private static final String CODE_CACHE_PREFIX = "code:";

	@Autowired
	private ValueOperations<String, Object> valueOperations;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public VerificationCodeAO addCode(VerificationCodeAO verificationCodeAO) {
		switch (verificationCodeAO.getType()) {
			case EMAIL:
				verificationCodeAO = detailEmailVerificationCode(verificationCodeAO);
				break;
			case MESSAGE:
				verificationCodeAO = detailMessageVerificationCode(verificationCodeAO);
				break;
			default:
				throw new RuntimeException("未找到指定格式的验证码处理方式。 请检查验证码类型是否支持！");
		}
		return verificationCodeAO;
	}

	@Override
	public VerificationCodeAO getCode(String businessKey) {
		VerificationCodeAO verificationCodeAO = (VerificationCodeAO) valueOperations.get(getVerifyCodeKey(businessKey));
		if (verificationCodeAO == null) {
			throw new RuntimeException("验证码已过期。 或者未申请验证码！");
		}
		return verificationCodeAO;
	}

	@Override
	public void clearCode(String businessKey) {
		redisTemplate.delete(getVerifyCodeKey(businessKey));
	}

	@Override
	public Boolean validateCode(String businessKey, String code) {
		VerificationCodeAO verificationCodeAO = (VerificationCodeAO) valueOperations.get(getVerifyCodeKey(businessKey));
		if (verificationCodeAO == null) {
			return false;
		} else if (StringUtils.equalsIgnoreCase(code, verificationCodeAO.getCode())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证码有效有效时间为 10 分钟， 1分钟中后可重新生成验证码。
	 * @param verificationCodeAO
	 * @return
	 */
	private VerificationCodeAO detailEmailVerificationCode(VerificationCodeAO verificationCodeAO) {
		String verifyCodeKey = getVerifyCodeKey(verificationCodeAO);
		if (!isRegenerate(verifyCodeKey)) {
			return getCode(verificationCodeAO.getBusinessKey());
		}

		// 设置过期时间（默认为10分钟）
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
		verificationCodeAO.setData(formatter.format(localDateTime));
		verificationCodeAO.setExpirationDate(60 * 10);
		verificationCodeAO.setCode(VerifyCodeUtil.generateVerifyCode(6));

		// 存入缓存
		valueOperations.set(verifyCodeKey, verificationCodeAO, 60 * 10, TimeUnit.SECONDS);

		// 将邮件发送到消息队列中
		EmailEntity emailEntity = EmailEntity.builder()
				.to(verificationCodeAO.getBusinessKey())
				.subject("验证码")
				.context(verificationCodeAO.getCode()).build();
		rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", emailEntity);
		return verificationCodeAO;

	}

	/**
	 * 短信接口没实现
 	 */
	private VerificationCodeAO detailMessageVerificationCode(VerificationCodeAO verificationCodeAO) {
		throw new RuntimeException("短信接口暂未实现！");
	}

	private String getVerifyCodeKey(VerificationCodeAO verificationCodeAO) {
		return CODE_CACHE_PREFIX + verificationCodeAO.getBusinessKey();
	}

	private String getVerifyCodeKey(String businessKey) {
		return CODE_CACHE_PREFIX + businessKey;
	}

	/**
	 * 某个key的验证码是否可以重新生成
	 * @param key 验证码key
	 * @return 是否能重新生成验证码
	 */
	private boolean isRegenerate(String key) {
		Long expire = redisTemplate.getExpire(key);
		if (expire < (60 * 10) - 60) {
			return true;
		}

		return false;
	}
}
