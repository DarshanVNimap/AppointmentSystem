package com.apointmentManagementSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDto {
	
	@NotBlank(message = "Email is required")
	@Email(message = "Enter valid email id")
	private String email;
	
	@NotBlank(message = "Password is required")
	private String password;

}
