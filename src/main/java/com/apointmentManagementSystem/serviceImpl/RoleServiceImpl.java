package com.apointmentManagementSystem.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apointmentManagementSystem.dto.RoleDto;
import com.apointmentManagementSystem.dto.RoleResponseDto;
import com.apointmentManagementSystem.entity.Role;
import com.apointmentManagementSystem.exception.ResourceAlreadyExistException;
import com.apointmentManagementSystem.exception.ResourceNotFoundException;
import com.apointmentManagementSystem.repository.IRolePermissionRepository;
import com.apointmentManagementSystem.repository.IRoleRepository;
import com.apointmentManagementSystem.service.RoleService;
import com.apointmentManagementSystem.utils.ErrorMessageConstant;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private IRoleRepository roleRepository;
	
	@Autowired
	private IRolePermissionRepository rolePermissionRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public RoleDto addRole(RoleDto roleDto, int userId) {

		if (roleRepository.existsByNameIgnoreCaseAndIsActiveTrue(roleDto.getName())) {
			throw new ResourceAlreadyExistException(ErrorMessageConstant.ROLE_ALREADY_EXIST);
		}
		Role createRole = new Role();
		createRole.setName(roleDto.getName().toUpperCase());
		createRole.setCreatedBy(userId);

		roleRepository.save(createRole);

		return roleDto;
	}

	@Override
	public List<RoleResponseDto> getAllRoles() {

		return roleRepository.findAllByIsActiveTrue().stream().map(role -> modelMapper.map(role, RoleResponseDto.class))
				.toList();

	}

	@Override
	public RoleDto updateRole(int roleId, RoleDto roleDto, int loggedUser) {

		Role role = roleRepository.findByIdAndIsActiveTrue(roleId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.ROLE_NOT_EXIST));

		if (roleRepository.existsByNameIgnoreCaseAndIsActiveTrue(roleDto.getName())) {
			throw new ResourceAlreadyExistException("Role " + roleDto.getName() + " already exist");
		}

		role.setName(roleDto.getName().toUpperCase());

		role.setUpdatedBy(loggedUser);
		roleRepository.save(role);

		return roleDto;
	}

	@Override
	public void deleteRole(int roleId, int loggedUser) {

		roleRepository.findByIdAndIsActiveTrue(roleId)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found"));

		roleRepository.deleteRoleById(roleId , loggedUser);
		rolePermissionRepository.deleteByRoleId(roleId, loggedUser);
		
//		rolePermissionRepository.deleteByRoleId(roleId);

	}

}
