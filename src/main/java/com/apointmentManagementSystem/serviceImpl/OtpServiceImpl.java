package com.apointmentManagementSystem.serviceImpl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apointmentManagementSystem.entity.OtpEntity;
import com.apointmentManagementSystem.repository.IOtpRepository;
import com.apointmentManagementSystem.service.EmailService;
import com.apointmentManagementSystem.service.OtpService;
import com.apointmentManagementSystem.utils.EmailTemplate;

import jakarta.mail.MessagingException;

@Service
public class OtpServiceImpl implements OtpService{
	
	@Autowired
	private IOtpRepository otpRepository;
	
	@Autowired
	private EmailService emailService;

	private void saveOtp(String email, String otp, int userId, LocalDateTime expiry) {
		
		otpRepository.deleteAllByEmail(email);
		
		
		
		OtpEntity otpEntity = new OtpEntity();
		otpEntity.setEmail(email);
		otpEntity.setExpireAt(expiry);
		otpEntity.setOtp(otp);
		otpEntity.setUserId(userId);
		 
		otpRepository.save(otpEntity);
		
		otpRepository.findAll().stream().forEach(System.out::println);
	}
	
	public void generateOtpAndSendEmail(String email , int userId , String name) throws MessagingException {
		
		final int otp = generateOpt();
		String otp1 = Integer.toString(otp);
		LocalDateTime expiryTime = LocalDateTime.now().plus(5, ChronoUnit.MINUTES);
		
		saveOtp(email, otp1, userId,expiryTime);
		
		String mailTemplate = EmailTemplate.FORGOT_PASSWORD.replaceAll("\\[USER NAME\\]", name).replaceAll("\\[OTP\\]", otp1);
		
		emailService.sendSimpleMessage(email, "Forgot password", mailTemplate);
		
	}
	
	private int generateOpt() {
			int min = 100000;
			int max = 999999;
			
			return  (int) Math.floor(Math.random() * (max - min +1) + min);
			
	}

}
