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
import com.apointmentManagementSystem.dto.UpdateUserDto;
import com.apointmentManagementSystem.dto.UserRequestDto;
import com.apointmentManagementSystem.service.UserService;
import com.apointmentManagementSystem.utils.ApiUrl;
import com.apointmentManagementSystem.utils.SuccessMessageConstant;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(ApiUrl.USERS)
@SecurityRequirement(name = "jwt")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping()
	public ResponseEntity<?> addUserDetail(@RequestBody UserRequestDto userRequest , @RequestAttribute(name = "X-user-id") int loggedUser) {
		try {
			userService.addUserDetail(userRequest, loggedUser);
			return ResponseEntity
					.ok(new MessageResponseDto(SuccessMessageConstant.USER_ADDED, null ,200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}
	}

	@PutMapping()
	public ResponseEntity<?> updateUserDetail(@RequestAttribute(name = "X-user-id") int loggedUser, @RequestBody UpdateUserDto userReuqest) {
		try {
			userService.updateUserDetail(userReuqest, loggedUser);
			return ResponseEntity
					.ok(new MessageResponseDto(SuccessMessageConstant.USER_UPDATED, null, 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping()
	public ResponseEntity<?> getAllUsers() {
		try {

			return ResponseEntity
					.ok(new MessageResponseDto(SuccessMessageConstant.USER_FETCHED, userService.getAllUserDetail(), 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable int id , @RequestAttribute(name = "X-user-id") int loggedUser) {
		try {

			return ResponseEntity
					.ok(new MessageResponseDto(SuccessMessageConstant.USER_FETCHED, userService.getUserById(id ,loggedUser), 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable int id , @RequestAttribute(name = "X-user-id") int loggedUser) {
		try {
			userService.deteleUser(id, loggedUser);
			return ResponseEntity
					.ok(new MessageResponseDto(SuccessMessageConstant.USER_DELETED, null, 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}
	}

}
