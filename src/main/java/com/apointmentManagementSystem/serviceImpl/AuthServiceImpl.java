package com.apointmentManagementSystem.serviceImpl;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apointmentManagementSystem.configuration.CustomeUserDetail;
import com.apointmentManagementSystem.dto.AuthRequestDto;
import com.apointmentManagementSystem.dto.AuthResponseDto;
import com.apointmentManagementSystem.dto.OtpDto;
import com.apointmentManagementSystem.dto.PasswordDto;
import com.apointmentManagementSystem.entity.OtpEntity;
import com.apointmentManagementSystem.entity.User;
import com.apointmentManagementSystem.exception.ResourceNotFoundException;
import com.apointmentManagementSystem.repository.IOtpRepository;
import com.apointmentManagementSystem.repository.IUserRepository;
import com.apointmentManagementSystem.service.AuthService;
import com.apointmentManagementSystem.service.JwtFilterService;
import com.apointmentManagementSystem.service.OtpService;
import com.apointmentManagementSystem.utils.ErrorMessageConstant;

import jakarta.mail.MessagingException;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private JwtFilterService jwtService;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private OtpService otpService;

	@Autowired
	private IOtpRepository otpRepository;

	@Override
	public AuthResponseDto authenticateUser(AuthRequestDto authRequest) throws UserPrincipalNotFoundException {

		authManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

		User getUser = userRepository.findByEmailIgnoreCaseAndIsActiveTrue(authRequest.getEmail())
				.orElseThrow(() -> new UserPrincipalNotFoundException(ErrorMessageConstant.INVALID_USERNAME_PASSWORD));

		String jwtToken = jwtService.generateToken(new CustomeUserDetail(getUser));

		return new AuthResponseDto(jwtToken, getUser.getId());

	}

	public void forgotPassword(OtpDto otpRequest) throws MessagingException {

		User user = userRepository.findByEmailIgnoreCaseAndIsActiveTrue(otpRequest.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.USER_NOT_EXIST));

		otpService.generateOtpAndSendEmail(user.getEmail(), user.getId(), user.getFirstName());

	}

	public void setNewPassword(PasswordDto passwordRequest) throws Exception {

		User user = userRepository.findByEmailIgnoreCaseAndIsActiveTrue(passwordRequest.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.USER_NOT_EXIST));

		OtpEntity getOtpEntity = otpRepository
				.findByEmailIgnoreCaseAndOtp(passwordRequest.getEmail(), passwordRequest.getOtp())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid request"));

		if (LocalDateTime.now().isAfter(getOtpEntity.getExpireAt())) {
			throw new Exception("OTP has expired.");
		}

		user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));

		userRepository.save(user);
	}

}
