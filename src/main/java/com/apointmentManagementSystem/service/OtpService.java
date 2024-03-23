package com.apointmentManagementSystem.service;

import jakarta.mail.MessagingException;

public interface OtpService {
	
	public void generateOtpAndSendEmail(String email , int userId , String name) throws MessagingException ;

}
