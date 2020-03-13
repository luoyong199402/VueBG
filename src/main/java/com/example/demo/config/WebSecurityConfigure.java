package com.example.demo.config;

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
import org.springframework.stereotype.Component;

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
	private CustomAuthenticationDetailsSource authenticationDetailsSource;

	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private VueUserDetailsServiceImpl vueUserDetailsService;

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
				//关闭Session
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
					//开放api路径
					.authorizeRequests()
					.antMatchers("/api/public/**")
					.permitAll()
					.anyRequest()
					.authenticated()
				//开启自动配置的登陆功能
				.and()
					//自定义登录请求路径(post请求)
					.formLogin()
					.usernameParameter("username")
					.passwordParameter("password")
					.loginProcessingUrl("/api/login")
					//验证成功处理器
					.successHandler(authenticationSuccessHandler)
					//验证失败处理器
					.failureHandler(authenticationFailureHandler)
					.authenticationDetailsSource(authenticationDetailsSource)
					.permitAll()
				.and()
					//关闭拦截未登录自动跳转,改为返回json信息
					.exceptionHandling()
					.authenticationEntryPoint(unAuthorizedEntryPoint())
				//开启自动配置的注销功能
				.and()
					.logout()
					.logoutUrl("/api/logout")
					//注销成功处理器
					.logoutSuccessHandler(logoutSuccessHandler)
					.permitAll()
				.and()
					.addFilter(new TokenLoginFilter(authenticationManagerBean()))
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
}
