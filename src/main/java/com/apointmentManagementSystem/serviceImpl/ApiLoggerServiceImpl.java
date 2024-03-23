package com.apointmentManagementSystem.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apointmentManagementSystem.entity.ApiLoggerEntity;
import com.apointmentManagementSystem.repository.IApiLoggerRepository;
import com.apointmentManagementSystem.service.ApiLoggerService;

@Service
public class ApiLoggerServiceImpl implements ApiLoggerService {
	
	@Autowired
	private IApiLoggerRepository apiLoggerRepository;

	@Override
	public void createLog(ApiLoggerEntity logs) {
		apiLoggerRepository.save(logs);
	}

}
