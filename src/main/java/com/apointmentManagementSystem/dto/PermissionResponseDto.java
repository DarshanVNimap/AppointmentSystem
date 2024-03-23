package com.apointmentManagementSystem.dto;

import com.apointmentManagementSystem.enumEntity.Method;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PermissionResponseDto {
	
	private int id;
	private String action;
	private String description;
	private Method method;
	private String baseUrl;

}
