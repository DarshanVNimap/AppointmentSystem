package com.apointmentManagementSystem.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apointmentManagementSystem.dto.ErrorMessageResponseDto;
import com.apointmentManagementSystem.dto.MessageResponseDto;
import com.apointmentManagementSystem.enumEntity.AppointmentResponse;
import com.apointmentManagementSystem.service.UserAppointmentService;
import com.apointmentManagementSystem.utils.SuccessMessageConstant;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/user-appointments")
@SecurityRequirement(name = "jwt")
public class UserAppointmentController {
	
	@Autowired
	private UserAppointmentService userAppointmentService;
	
	
	@PatchMapping("/{appointment-id}")
	@PreAuthorize("hasAuthority('APPOINTMENT_RESPONSE::UPDATE')")
	public ResponseEntity<?> updateAppointmentResponse(@PathVariable("appointment-id") int appointmentId , @RequestParam("response") AppointmentResponse response , @RequestAttribute("X-user-id") int loggedInUser) {
		try {
			userAppointmentService.updateAppointmentResponse(appointmentId, response, loggedInUser);
			return ResponseEntity.ok(new MessageResponseDto(SuccessMessageConstant.APPOINTMENT_RESPONSE, null, 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}

	}
	
	@GetMapping("/meeting-report")
	@PreAuthorize("hasAuthority('REPORT::READ')")
	public ResponseEntity<?> getMeetingReportOfUser(@RequestParam(name = "startDate") LocalDate startDate, @RequestParam(name = "endDate") LocalDate endDate , @RequestAttribute("X-user-id") int loggedInUser) {
		try {
			
			return ResponseEntity.ok(new MessageResponseDto(SuccessMessageConstant.APPOINTMENT_RECORD_FETCH, userAppointmentService.getMeetingRecord(startDate , endDate, loggedInUser), 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}

	}

	
	

}
