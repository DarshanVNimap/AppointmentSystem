package com.apointmentManagementSystem.serviceImpl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.apointmentManagementSystem.entity.User;
import com.apointmentManagementSystem.repository.IUserRepository;
import com.apointmentManagementSystem.service.BulkUploadService;
import com.apointmentManagementSystem.service.EmailService;
import com.apointmentManagementSystem.utils.EmailTemplate;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BulkUploadServiceImpl implements BulkUploadService {

	@Autowired
	private FileServiceImpl fileService;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;

	@Override
	public void importUserFromExcel(MultipartFile file, int loggedInUser) throws Exception {

		List<User> users = fileService.importExcel(file, loggedInUser);

		users.stream().forEach(u -> {

			String template = EmailTemplate.INVITAIION_MAIL.replaceAll("\\[Username\\]", u.getFirstName());
			template = template.replaceAll("\\[User's Email\\]", u.getEmail());
			template = template.replaceAll("\\[User's Password\\]", u.getPassword());

				try {
					emailService.sendSimpleMessage(u.getEmail(), "Invitation to Join Our Platform", template);
				} catch (MessagingException e) {
					
					log.error(e.getMessage());
					
				}

		});

		List<User> newUsers = users.stream().map(user -> {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			return user;
		}).toList();
		
		userRepository.saveAll(newUsers);	

	}

	@Override
	public byte[] downloadFile(String filename , int loggedInUser) throws IOException, Exception {
		return fileService.downloadFile(filename, loggedInUser);
	}

}
