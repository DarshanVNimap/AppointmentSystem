package com.apointmentManagementSystem.service;

import jakarta.mail.MessagingException;

public interface EmailService {
	
	public void sendSimpleMessage(String emailTo, String subject, String body) throws MessagingException ;

}
