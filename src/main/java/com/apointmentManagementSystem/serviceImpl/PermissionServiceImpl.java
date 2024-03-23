package com.apointmentManagementSystem.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apointmentManagementSystem.dto.PermissionDto;
import com.apointmentManagementSystem.dto.PermissionResponseDto;
import com.apointmentManagementSystem.entity.Permission;
import com.apointmentManagementSystem.exception.ResourceAlreadyExistException;
import com.apointmentManagementSystem.exception.ResourceNotFoundException;
import com.apointmentManagementSystem.repository.IPermissionRepository;
import com.apointmentManagementSystem.repository.IRolePermissionRepository;
import com.apointmentManagementSystem.service.PermissionService;
import com.apointmentManagementSystem.utils.ErrorMessageConstant;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private IPermissionRepository permissionRepository;

	@Autowired
	private IRolePermissionRepository rolePermissionRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PermissionResponseDto addPermission(PermissionDto permissionDto, int userId) throws Exception {
		if (permissionRepository.existsByActionIgnoreCaseAndIsActiveTrue(permissionDto.getActionName())) {
			throw new ResourceAlreadyExistException(ErrorMessageConstant.PERMISSION_ALREADY_EXIST);
		}
		Permission permission = new Permission();

		permission.setAction(permissionDto.getActionName());
		permission.setBaseUrl(permissionDto.getBaseUrl());
		permission.setDescription(permissionDto.getDescription());
		permission.setMethod(permissionDto.getMethod());
		permission.setCreatedBy(userId);

		return modelMapper.map(permissionRepository.save(permission), PermissionResponseDto.class);

	}

	@Override
	public List<PermissionResponseDto> getAllPermissions() {

		return permissionRepository.findAllByIsActiveTrue().stream()
				.map(permission -> modelMapper.map(permission, PermissionResponseDto.class)).toList();

	}

	@Override
	public PermissionResponseDto updatePermission(int permissionId, PermissionDto permissionDto, int userId) {
		Permission getPermission = permissionRepository.findByIdAndIsActiveTrue(permissionId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.PERMISSION_NOT_EXIST));

		getPermission.setAction(permissionDto.getActionName());
		getPermission.setBaseUrl(permissionDto.getBaseUrl());
		getPermission.setDescription(permissionDto.getDescription());
		getPermission.setMethod(permissionDto.getMethod());
		getPermission.setUpdatedBy(userId);

		return modelMapper.map(permissionRepository.save(getPermission), PermissionResponseDto.class);
	}

	@Override
	public void deletePermission(int permissionId, int loggedUser) {

		permissionRepository.findByIdAndIsActiveTrue(permissionId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.PERMISSION_NOT_EXIST));
		permissionRepository.deleteById(permissionId , loggedUser);
		rolePermissionRepository.deleteByPermissionId(permissionId , loggedUser);

	}

}
