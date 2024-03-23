package com.apointmentManagementSystem.service;

import java.util.List;

import com.apointmentManagementSystem.dto.RoleDto;
import com.apointmentManagementSystem.dto.RoleResponseDto;

public interface RoleService {
	
	public RoleDto addRole(RoleDto roleDto, int loggedUser);

	public List<RoleResponseDto> getAllRoles();

	public RoleDto updateRole(int roleId, RoleDto roleDto, int loggedUser);

	public void deleteRole(int roleId , int loggedUser);


}
