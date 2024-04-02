package com.apointmentManagementSystem.service;

import java.time.LocalDate;
import java.util.List;

import com.apointmentManagementSystem.dto.AppointmentRequestDto;
import com.apointmentManagementSystem.dto.AppointmentResponseDto;

public interface AppointmentService {
	
	void createAppointment(AppointmentRequestDto appointmentRequest , int loggedUser);
	void updateApointment(int id , int loggedUser);
	void deleteAppointment(int id  , int loggedUser);
	AppointmentResponseDto getAppointmentById(int id);
	List<?> getAllAppointment(LocalDate fromDate , LocalDate toDate, int loggedInUser);

	
}
