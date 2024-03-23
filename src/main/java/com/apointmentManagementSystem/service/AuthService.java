package com.apointmentManagementSystem.service;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import com.apointmentManagementSystem.dto.AuthRequestDto;
import com.apointmentManagementSystem.dto.AuthResponseDto;
import com.apointmentManagementSystem.dto.OtpDto;
import com.apointmentManagementSystem.dto.PasswordDto;

import jakarta.mail.MessagingException;

public interface AuthService {
	
	
	AuthResponseDto authenticateUser(AuthRequestDto authRequest) throws UserPrincipalNotFoundException;
	void forgotPassword(OtpDto otp) throws MessagingException;
	public void setNewPassword(PasswordDto passwordRequest) throws Exception;

}
