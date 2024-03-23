package com.apointmentManagementSystem.interceptor;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.apointmentManagementSystem.entity.ApiLoggerEntity;
import com.apointmentManagementSystem.service.ApiLoggerService;
import com.apointmentManagementSystem.utils.ApiUrl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ApiLogger implements HandlerInterceptor{
	
	@Autowired
	 private ApiLoggerService apiService;
	
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		ArrayList<String> urlsWithoutHeader = new ArrayList<>(Arrays.asList(ApiUrl.URLS_WITHOUT_HEADER));
		final String requestUrl = request.getRequestURI();
		
		if(!urlsWithoutHeader.contains(requestUrl)) {
			ApiLoggerEntity log = new ApiLoggerEntity();
			log.setUrl(requestUrl);
			log.setHost(request.getRemoteHost());
			log.setIpAddress(request.getRemoteAddr());
			log.setMethod(request.getMethod());
			
			apiService.createLog(log);
			
		}
		
		return true;
	}

}
