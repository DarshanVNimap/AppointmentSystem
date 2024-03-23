package com.apointmentManagementSystem.dto;

import com.apointmentManagementSystem.enumEntity.Method;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionDto {
	
	private String actionName;
	private String description;
	private Method method;
	private String baseUrl;


}
