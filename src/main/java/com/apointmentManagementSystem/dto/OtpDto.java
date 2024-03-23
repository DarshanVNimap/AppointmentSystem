package com.apointmentManagementSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpDto {
	
	@NotBlank(message = "Email required")
	@Email(message = "Enter valid email")
	private String email;

}
