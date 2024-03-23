package com.apointmentManagementSystem.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apointmentManagementSystem.dto.UpdateUserDto;
import com.apointmentManagementSystem.dto.UserRequestDto;
import com.apointmentManagementSystem.dto.UserResponseDto;
import com.apointmentManagementSystem.entity.Role;
import com.apointmentManagementSystem.entity.User;
import com.apointmentManagementSystem.exception.ResourceAlreadyExistException;
import com.apointmentManagementSystem.exception.ResourceNotFoundException;
import com.apointmentManagementSystem.repository.IRoleRepository;
import com.apointmentManagementSystem.repository.IUserRepository;
import com.apointmentManagementSystem.service.EmailService;
import com.apointmentManagementSystem.service.UserService;
import com.apointmentManagementSystem.utils.EmailTemplate;
import com.apointmentManagementSystem.utils.ErrorMessageConstant;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private IRoleRepository roleRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private EmailService emailService;

	@Override
	public void addUserDetail(UserRequestDto userRequest , int loggedIn) {
		
		if(userRepository.existsByEmailIgnoreCaseAndIsActiveTrue(userRequest.getEmail())) {
			throw new ResourceAlreadyExistException(ErrorMessageConstant.USER_ALREADY_EXIST);
		}
		Role getRole  = roleRepository.findByIdAndIsActiveTrue(userRequest.getRole())
								   .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.ROLE_NOT_EXIST));
		
		String newPassword = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
		
		User newUser = new User();
		
		newUser.setFirstName(userRequest.getFirstName());
		newUser.setLastName(userRequest.getLastName());
		newUser.setEmail(userRequest.getEmail());
		newUser.setPassword(encoder.encode(newPassword));
		newUser.setRole(getRole);
		newUser.setCreatedBy(loggedIn);
		
		userRepository.save(newUser);
		
		String template = EmailTemplate.INVITAIION_MAIL.replaceAll("\\[Username\\]", userRequest.getFirstName());
		template = template.replaceAll("\\[User's Email\\]", userRequest.getEmail());
		template = template.replaceAll("\\[User's Password\\]", newPassword);

			try {
				emailService.sendSimpleMessage(userRequest.getEmail(), "Invitation to Join Our Platform", template);
			} catch (MessagingException e) {
				
				log.error(e.getMessage());
				
			}

	}

	@Override
	public void updateUserDetail(UpdateUserDto userDto , int loggedIn) {
		
		User updateUser = userRepository.findByIdAndIsActiveTrue(loggedIn)
										.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.USER_NOT_EXIST));
		
		updateUser.setFirstName(userDto.getFirstName());
		updateUser.setLastName(userDto.getLastName());
		
		userRepository.save(updateUser);
		
	}

	@Override
	public void deteleUser(int id , int loggedIn) {
		
		userRepository.findByIdAndIsActiveTrue(id)
					  .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.USER_NOT_EXIST));
		
		userRepository.deleteById(id, loggedIn);
		
			
	}


	@Override
	public List<UserResponseDto> getAllUserDetail() {
		
		return userRepository.findAllByIsActiveTrue().stream()
													 .map(user -> modelMapper.map(user, UserResponseDto.class))
													 .toList();
	}

	@Override
	public UserResponseDto getUserById(int id, int loggedUser) {

		User loggedInUser = userRepository.findByIdAndIsActiveTrue(loggedUser)
				  .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.USER_NOT_EXIST));
		 
		if(loggedInUser.getRole().getName().equals("ADMIN")) {
			User getUser = userRepository.findByIdAndIsActiveTrue(id)
					  .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.USER_NOT_EXIST));
			
			return modelMapper.map(getUser, UserResponseDto.class);
		}
		
		return modelMapper.map(loggedInUser, UserResponseDto.class);
	}

}
