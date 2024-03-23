package com.apointmentManagementSystem.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.apointmentManagementSystem.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentResponseDto {

	int id;
	LocalDateTime appointmentDate;	
	List<User> developers;
}
