package com.apointmentManagementSystem.service;

import java.util.List;

import com.apointmentManagementSystem.IDtos.AppointmentResponseIDto;
import com.apointmentManagementSystem.dto.AppointmentRequestDto;
import com.apointmentManagementSystem.dto.AppointmentResponseDto;

public interface AppointmentService {
	
	void createAppointment(AppointmentRequestDto appointmentRequest , int loggedUser);
	void updateApointment(int id , int loggedUser);
	void deleteAppointment(int id  , int loggedUser);
	AppointmentResponseDto getAppointmentById(int id);
	List<AppointmentResponseIDto> getAllAppointment();

	
}
