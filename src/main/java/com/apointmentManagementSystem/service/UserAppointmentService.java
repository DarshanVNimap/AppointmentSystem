package com.apointmentManagementSystem.service;

import java.time.LocalDate;

import com.apointmentManagementSystem.IDtos.RecordFilterResponseDto;
import com.apointmentManagementSystem.enumEntity.AppointmentResponse;

public interface UserAppointmentService {
	
	void updateAppointmentResponse(int appointmentId , AppointmentResponse response , int loggedInUser);
	public RecordFilterResponseDto getMeetingRecord(LocalDate startDate , LocalDate endDate , int loggedInUser);

}
