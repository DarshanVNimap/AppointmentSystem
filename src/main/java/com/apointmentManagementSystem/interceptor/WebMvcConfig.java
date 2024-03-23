package com.apointmentManagementSystem.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private ApiLogger apiLog;
	
	@Autowired
	private AuthLogger authLog;
	
	
	
	public WebMvcConfig() {
		super();
		// TODO Auto-generated constructor stub
	}



	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(apiLog);
		registry.addInterceptor(authLog);
		WebMvcConfigurer.super.addInterceptors(registry);
	}
	
	

}
