package com.apointmentManagementSystem.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentRequestDto {
	
	LocalDateTime appointmentDate;
	List<Integer> developer;

}
