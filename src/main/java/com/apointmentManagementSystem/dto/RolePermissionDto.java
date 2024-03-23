package com.apointmentManagementSystem.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolePermissionDto {
	
	private int roleId;
	private List<Integer> permissionIds;


}
