package com.apointmentManagementSystem.service;

import java.util.List;

import com.apointmentManagementSystem.dto.PermissionResponseDto;
import com.apointmentManagementSystem.dto.RolePermissionDto;

public interface RolePermissionService {
	
	public void assignPermissionToRole(RolePermissionDto rp, int  loggedInUser) ;
	public void removePermissionFromRole(int permissionId , int roleId , int loggedInUser);
	public List<PermissionResponseDto> getAllPermissionOfRole(int roleId);


}
