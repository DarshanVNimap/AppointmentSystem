package com.apointmentManagementSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDto {
	
	@NotBlank(message = "Enter password")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "Invalid password format*Invalid Format")
	private String password;
	
	@NotBlank(message = "Enter email")
	@Email(message = "Enter valid email")
	private String email;

	@NotBlank(message = "Enter otp")
	private String otp;

}
