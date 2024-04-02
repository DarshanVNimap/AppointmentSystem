	package com.apointmentManagementSystem.controller;

import java.time.LocalDate;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apointmentManagementSystem.dto.AppointmentRequestDto;
import com.apointmentManagementSystem.dto.ErrorMessageResponseDto;
import com.apointmentManagementSystem.dto.MessageResponseDto;
import com.apointmentManagementSystem.service.AppointmentService;
import com.apointmentManagementSystem.utils.SuccessMessageConstant;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/appointments")
@SecurityRequirement(name = "jwt")

public class AppointmentController {
	
	@Autowired
	private AppointmentService appointmentService;

	@GetMapping
	@PreAuthorize("hasAuthority('APPOINTMENT::READ')")
	public ResponseEntity<?> getAllApointments(@RequestParam(required = false , name = "from") LocalDate fromDate , @RequestParam(required = false , name = "to") LocalDate toDate,  @RequestAttribute("X-user-id") int loggedInUser) {
		try {

			return ResponseEntity.ok(new MessageResponseDto(SuccessMessageConstant.APPOINTMENT_FETCHED, appointmentService.getAllAppointment(fromDate, toDate, loggedInUser), 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}

	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('APPOINTMENT::READ')")
	public ResponseEntity<?> getApointmentById(@PathVariable("id") int id) {

		try {

			return ResponseEntity.ok(new MessageResponseDto(SuccessMessageConstant.APPOINTMENT_FETCHED, appointmentService.getAppointmentById(id), 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}

	}

	@PostMapping
	@PreAuthorize("hasAuthority('APPOINTMENT::CREATE')")
	public ResponseEntity<?> createApointment(@RequestBody AppointmentRequestDto appointmentDto , @RequestAttribute("X-user-id") int loggedInUser) {

		try {
			appointmentService.createAppointment(appointmentDto, loggedInUser);
			return ResponseEntity.ok(new MessageResponseDto(SuccessMessageConstant.APPOINTMENT_ADDED, null, 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}

	}

//	@PutMapping("/{id}")
//	public ResponseEntity<?> updateApointment() {
//
//		try {
//
//			return ResponseEntity.ok(new MessageResponseDto(SuccessMessageConstant.ROLE_ADDED, null, 200));
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
//		}
//
//	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('APPOINTMENT::DELETE')")
	public ResponseEntity<?> deleteApointment(@PathVariable("id") int id , @RequestAttribute("X-user-id") int loggedInUser) {

		try {
			appointmentService.deleteAppointment(id, loggedInUser);
			return ResponseEntity.ok(new MessageResponseDto(SuccessMessageConstant.APPOINTMENT_DELETED, null, 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}

	}

}
