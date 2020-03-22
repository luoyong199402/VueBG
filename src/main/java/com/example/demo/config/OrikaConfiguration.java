package com.example.demo.config;

import com.example.demo.orika.OrikaBeanMapper;
import com.example.demo.orika.PageConvertMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ly
 */
@Configuration
public class OrikaConfiguration {
	@Bean
	public OrikaBeanMapper beanMapper() {
		return new OrikaBeanMapper();
	}

	@Bean
	public PageConvertMapper pageConvertMapper() {
		return new PageConvertMapper(beanMapper());
	}
}
