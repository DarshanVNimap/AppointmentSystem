package com.apointmentManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apointmentManagementSystem.dto.ErrorMessageResponseDto;
import com.apointmentManagementSystem.dto.MessageResponseDto;
import com.apointmentManagementSystem.dto.RolePermissionDto;
import com.apointmentManagementSystem.service.RolePermissionService;
import com.apointmentManagementSystem.utils.SuccessMessageConstant;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/role-permission")
@SecurityRequirement(name = "jwt")
@PreAuthorize("hasRole('ADMIN')")
public class RolePermissionController {
	
	@Autowired
	private RolePermissionService rolePemissionService;
	
	
	@PostMapping
	public ResponseEntity<?> assignPermissionToRole(@RequestBody RolePermissionDto rolePermissionDto , @RequestAttribute("X-user-id") int loggedInUser) {
		try {
			rolePemissionService.assignPermissionToRole(rolePermissionDto, loggedInUser) ;
			return ResponseEntity
					.ok(new MessageResponseDto(SuccessMessageConstant.ROLE_PERMISSION_ASSIGN, null,200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}
	}

	@GetMapping("/role/{role-id}")
	public ResponseEntity<?> getAllPermissionOfRole(@PathVariable(name = "role-id") int roleId ) {
		try {
			
			return ResponseEntity
					.ok(new MessageResponseDto(SuccessMessageConstant.ROLE_PERMISSION_FETCHED, rolePemissionService.getAllPermissionOfRole(roleId) ,200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}
	}

	@DeleteMapping("/role/{role-id}/permission/{permission-id}")
	public ResponseEntity<?> removePermissionFromRole(@PathVariable(name = "role-id") int roleId, @PathVariable(name = "permission-id") int permissionId , @RequestAttribute("X-user-id") int loggedInUser) {
		try {
			rolePemissionService.removePermissionFromRole(permissionId, roleId, loggedInUser);
			return ResponseEntity
					.ok(new MessageResponseDto(SuccessMessageConstant.ROLE_PERMISSION_DELETE, null ,200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}
	}

}
