package com.apointmentManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.apointmentManagementSystem.dto.ErrorMessageResponseDto;
import com.apointmentManagementSystem.dto.MessageResponseDto;
import com.apointmentManagementSystem.service.BulkUploadService;
import com.apointmentManagementSystem.utils.SuccessMessageConstant;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/files")
@SecurityRequirement(name = "jwt")
@PreAuthorize("hasRole('ADMIN')")
public class BulkUploadController {
	
	@Autowired
	private BulkUploadService bulkUploadService;
	
	
	@PostMapping("/upload")
	public ResponseEntity<?> importUsers(@RequestParam("file") MultipartFile file , @RequestAttribute("X-user-id") int loggedInUser){
		try {
			bulkUploadService.importUserFromExcel(file, loggedInUser);
			return ResponseEntity.ok(new MessageResponseDto(SuccessMessageConstant.FILE_UPLOADED, null , 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}

	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllUploadedFiles(){
		try {
			return ResponseEntity.ok(new MessageResponseDto(SuccessMessageConstant.FILES_FETCHED, null, 200));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}

	}
	
	@GetMapping("/download")
	public ResponseEntity<?> exportUsers(@RequestParam("file") String fileName ,@RequestAttribute("X-user-id") int loggedInUser ){
		try {
			 ;
			return ResponseEntity.status(HttpStatus.OK)
								  .header("Content-Disposition", "attachment; filename="+fileName)
								  .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
								  .body(bulkUploadService.downloadFile(fileName , loggedInUser));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ErrorMessageResponseDto(e.getMessage(), 400));
		}

	}
	

}
