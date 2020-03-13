package com.example.demo.token;

/**
 * token管理接口
 */
public interface TokenManager {
	String createToken(Object object);

	Object getToken(String token);

	void removeToken(String token);
}
