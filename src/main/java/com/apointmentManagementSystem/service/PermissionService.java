package com.apointmentManagementSystem.service;

import java.util.List;

import com.apointmentManagementSystem.dto.PermissionDto;
import com.apointmentManagementSystem.dto.PermissionResponseDto;

public interface PermissionService {
	
	public PermissionResponseDto addPermission(PermissionDto pDto, int loggedUser) throws Exception;
	public List<PermissionResponseDto> getAllPermissions() ;
	public PermissionResponseDto updatePermission(int permissionId, PermissionDto permissionDto, int loggedUser);
	public void deletePermission(int permissionId ,  int loggedUser) ;

	

}
