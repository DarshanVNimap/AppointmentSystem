package com.apointmentManagementSystem.IDtos;

import java.time.LocalDate;
import java.time.LocalTime;

public interface UserAppointmentResponseIDto {
	
	int getAppointmentId();
	LocalDate getAppointmentDate();
	LocalTime getAppointmentTime();
	int getManagerId();
	String getManager();

}
