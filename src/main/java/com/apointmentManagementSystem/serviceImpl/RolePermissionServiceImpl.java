package com.apointmentManagementSystem.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apointmentManagementSystem.dto.PermissionResponseDto;
import com.apointmentManagementSystem.dto.RolePermissionDto;
import com.apointmentManagementSystem.entity.Permission;
import com.apointmentManagementSystem.entity.Role;
import com.apointmentManagementSystem.entity.RolePermission;
import com.apointmentManagementSystem.exception.ResourceNotFoundException;
import com.apointmentManagementSystem.repository.IPermissionRepository;
import com.apointmentManagementSystem.repository.IRolePermissionRepository;
import com.apointmentManagementSystem.repository.IRoleRepository;
import com.apointmentManagementSystem.service.RolePermissionService;
import com.apointmentManagementSystem.utils.ErrorMessageConstant;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

	@Autowired
	private IRolePermissionRepository rolePermissionRepository;

	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private IPermissionRepository permissionRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void assignPermissionToRole(RolePermissionDto rolePermissionDto, int loggedInUser) {
		Role getRole = roleRepository.findByIdAndIsActiveTrue(rolePermissionDto.getRoleId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.ROLE_NOT_EXIST));

		List<Permission> getAllPermissions = permissionRepository
				.findByIdInAndIsActiveTrue(rolePermissionDto.getPermissionIds());

		if (getAllPermissions.size() != rolePermissionDto.getPermissionIds().size()) {
			throw new ResourceNotFoundException(ErrorMessageConstant.PERMISSION_NOT_EXIST);
		}

		List<RolePermission> newRolePermission = getAllPermissions.stream()
				.filter(permission -> !rolePermissionRepository
						.existsByRoleIdAndPermissionIdAndIsActiveTrue(getRole.getId(), permission.getId()))
				.map(permission -> {
					RolePermission rolePermisssion = new RolePermission();
					rolePermisssion.setRole(getRole);
					rolePermisssion.setPermission(permission);
					rolePermisssion.setCreatedBy(loggedInUser);
					return rolePermisssion;
				}).toList();

		rolePermissionRepository.saveAll(newRolePermission);

	}

	@Override
	public void removePermissionFromRole(int permissionId, int roleId, int loggedInUser) {
		if (!rolePermissionRepository.existsByRoleIdAndPermissionIdAndIsActiveTrue(roleId, permissionId)) {
			throw new ResourceNotFoundException("");
		}

		rolePermissionRepository.deleteByroleIdAndPermissionId(roleId, permissionId, loggedInUser);

	}

	@Override
	public List<PermissionResponseDto> getAllPermissionOfRole(int roleId) {
		return rolePermissionRepository.findAllByRoleIdAndIsActiveTrue(roleId).stream()
				.map(rolePermission -> modelMapper.map(rolePermission.getPermission(), PermissionResponseDto.class))
				.toList();
	}

}
