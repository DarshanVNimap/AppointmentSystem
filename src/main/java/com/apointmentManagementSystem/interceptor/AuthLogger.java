package com.apointmentManagementSystem.interceptor;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.apointmentManagementSystem.entity.User;
import com.apointmentManagementSystem.repository.IUserRepository;
import com.apointmentManagementSystem.service.JwtFilterService;
import com.apointmentManagementSystem.utils.ApiUrl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthLogger implements HandlerInterceptor {
	
	@Autowired
	private JwtFilterService jwtService;
	
	@Autowired
	private IUserRepository userRepository;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		ArrayList<String> urlsWithoutHeader = new ArrayList<>(Arrays.asList(ApiUrl.URLS_WITHOUT_HEADER));

		final String authHeader = request.getHeader("Authorization");

		String token = (authHeader != null) ? authHeader.split(" ")[1] : null;

		final String requestURI = request.getRequestURI();

		if (!urlsWithoutHeader.contains(requestURI)) {
			if (token != null) {
				final String emailString = jwtService.extarctUsername(token);
				User getUser = userRepository.findByEmailIgnoreCaseAndIsActiveTrue(emailString).get();
				request.setAttribute("X-user-id", getUser.getId());
			}
		}

		// TODO Auto-generated method stub
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	

}
