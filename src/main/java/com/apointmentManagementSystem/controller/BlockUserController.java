package com.apointmentManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apointmentManagementSystem.dto.BlockUserDto;
import com.apointmentManagementSystem.dto.ErrorMessageResponseDto;
import com.apointmentManagementSystem.dto.MessageResponseDto;
import com.apointmentManagementSystem.service.BlockUserService;
import com.apointmentManagementSystem.utils.SuccessMessageConstant;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/block-users")
@SecurityRequirement(name = "jwt")
public class BlockUserController {
	
	@Autowired
	private BlockUserService blockUserService;

	@PostMapping("")
	@PreAuthorize("hasAuthority('BLOCKUSER::CREATE')")
	public ResponseEntity<?> blockUser(@RequestBody BlockUserDto blockUser , @RequestAttribute("X-user-id") int loggedInUser) {
		try {
			blockUserService.blockUser(blockUser, loggedInUser);
			return ResponseEntity.ok(new MessageResponseDto(SuccessMessageConstant.BLOCK_USER_ADD, null, 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}

	}

	@GetMapping()
	@PreAuthorize("hasAuthority('BLOCKUSER::READ')")
	public ResponseEntity<?> getBlockListOfUser(@RequestAttribute("X-user-id") int loggedInUser) {
		try {

			return ResponseEntity.ok(new MessageResponseDto(SuccessMessageConstant.APPOINTMENT_FETCHED, blockUserService.getBlockListOfUser(loggedInUser), 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}

	}

	
}
