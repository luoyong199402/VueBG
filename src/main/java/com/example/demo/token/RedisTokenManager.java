package com.example.demo.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class RedisTokenManager implements TokenManager {

	/**
	 * 前缀
 	 */
	private static final String TOKEN_CACHE_PREFIX = "token:";

	/**
	 * token过期时间
	 */
	private static final long TOKEN_EXPIRE_TIME = 60 * 60 * 4;

	@Autowired
	private ValueOperations<String, Object> valueOperations;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public String createToken(Object object) {
		String token = generateTokenKey();
		valueOperations.set(token, object, TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
		return token;
	}

	@Override
	public Object getToken(String token) {
		return valueOperations.get(token);
	}

	@Override
	public void removeToken(String token) {
		redisTemplate.delete(token);
	}

	private String generateTokenKey() {
		return TOKEN_CACHE_PREFIX + UUID.randomUUID().toString();
	}

	public static long getTokenExpireTime() {
		return TOKEN_EXPIRE_TIME;
	}
}
