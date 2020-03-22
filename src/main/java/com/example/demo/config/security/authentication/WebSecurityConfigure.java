package com.example.demo.config.security.authentication;

import com.example.demo.filter.TokenAuthenticationFilter;
import com.example.demo.filter.TokenLoginFilter;
import com.example.demo.token.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@Component
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {
	/**
	 * 自定义登录认证
	 */
	@Autowired
	private SelfAuthenticationProvider authenticationProvider;

	/**
	 * 自定义登录成功处理器
	 */
	@Autowired
	private UrlAuthenticationSuccessHandler authenticationSuccessHandler;

	/**
	 * 自定义登录失败处理器
	 */
	@Autowired
	private UrlAuthenticationFailureHandler authenticationFailureHandler;

	/**
	 * 自定义注销处理器
	 */
	@Autowired
	private UrlLogoutSuccessHandler logoutSuccessHandler;

	@Autowired
	private TokenManager tokenManager;

	/**
	 * 登录认证
	 * @param auth 登陆管理器
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//添加自定义登陆认证
		auth.authenticationProvider(authenticationProvider);
	}


	/**
	 * 具体配置登陆细节
	 * @param http 登陆访问对象
	 * @throws Exception 登陆异常
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//关闭csrf
		http.csrf().disable()
				.cors().configurationSource(corsConfigurationSource())
				.and()
				//关闭Session
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
					//开放api路径
					.authorizeRequests()
					.antMatchers("/api/public/**", "/api/login", "/api/logout")
					.permitAll()
					.anyRequest()
					.authenticated()
				//开启自动配置的登陆功能
				.and()
					//关闭拦截未登录自动跳转,改为返回json信息
					.exceptionHandling()
					.authenticationEntryPoint(unAuthorizedEntryPoint())
				//开启自动配置的注销功能
				.and()
					.logout()
					//注销成功处理器
					.logoutUrl("/api/logout")
					.logoutSuccessHandler(logoutSuccessHandler)
					.permitAll()
				.and()
					// 处理token认证的过滤器。
					.addFilterAt(tokenLoginFilter(), UsernamePasswordAuthenticationFilter.class)
					.addFilter(new TokenAuthenticationFilter(authenticationManagerBean(), tokenManager));
	}

	/**
	 * 身份认证失败处理类
	 * @return AuthenticationEntryPoint
	 */
	@Bean
	public AuthenticationEntryPoint unAuthorizedEntryPoint() {
		return new UnAuthorizedEntryPoint();
	}

	/**
	 * 重写方法，是上下文可以获取本地缓存对象
	 * @return AuthenticationManager  本地缓存对象
	 * @throws Exception 异常
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public TokenLoginFilter tokenLoginFilter() throws Exception {
		TokenLoginFilter tokenLoginFilter = new TokenLoginFilter(authenticationManagerBean());
		tokenLoginFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		tokenLoginFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
		return tokenLoginFilter;
	}

	private CorsConfigurationSource corsConfigurationSource() {
		CorsConfigurationSource source =   new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*");	//同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
		corsConfiguration.addAllowedHeader("*");//header，允许哪些header，本案中使用的是token，此处可将*替换为token；
		corsConfiguration.addAllowedMethod("*");	//允许的请求方法，PSOT、GET等
		((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**", corsConfiguration); //配置允许跨域访问的url
		return source;
	}

}
