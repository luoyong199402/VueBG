package com.example.demo.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenStore {
	private Collection<? extends GrantedAuthority> authorities;
	private Object credentials;
	private Object details;
	private Object principal;
	private Boolean isAuthenticated;
}
