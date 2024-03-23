package com.apointmentManagementSystem.IDtos;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentResponseIDto {
	
	int getId();
	LocalDateTime getAppointmentDate();
	List<UserAppointmentIDto> getAppointment();

}
;