package com.apointmentManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apointmentManagementSystem.dto.ErrorMessageResponseDto;
import com.apointmentManagementSystem.dto.MessageResponseDto;
import com.apointmentManagementSystem.dto.PermissionDto;
import com.apointmentManagementSystem.service.PermissionService;
import com.apointmentManagementSystem.utils.SuccessMessageConstant;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/permissions")
@SecurityRequirement(name = "jwt")
@PreAuthorize("hasRole('ADMIN')")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

	@GetMapping
	public ResponseEntity<?> getAllPermissions() {
		try {

			return ResponseEntity.ok(new MessageResponseDto(SuccessMessageConstant.PERMISSION_FETCHED,
					permissionService.getAllPermissions(), 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}
	}

	@PostMapping
	public ResponseEntity<?> addPermission(@RequestBody PermissionDto permissionDto,
			@RequestAttribute(name = "X-user-id") int loggedUser) {
		try {

			return ResponseEntity.ok(new MessageResponseDto(SuccessMessageConstant.PERMISSION_ADDED,
					permissionService.addPermission(permissionDto, loggedUser), 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updatePermission(@PathVariable("id") int id, @RequestBody PermissionDto permissionDto,
			@RequestAttribute(name = "X-user-id") int loggedUser) {
		try {
			return ResponseEntity.ok(new MessageResponseDto(SuccessMessageConstant.PERMISSION_UPDATED,
					permissionService.updatePermission(id, permissionDto, loggedUser), 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePermission(@PathVariable("id") int id,
			@RequestAttribute(name = "X-user-id") int loggedUser) {
		try {

			permissionService.deletePermission(id, loggedUser);
			return ResponseEntity.ok(new MessageResponseDto(SuccessMessageConstant.PERMISSION_DELETED, null, 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}
	}

}
