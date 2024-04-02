package com.apointmentManagementSystem.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apointmentManagementSystem.dto.AppointmentRequestDto;
import com.apointmentManagementSystem.dto.AppointmentResponseDto;
import com.apointmentManagementSystem.entity.AppointmentEntity;
import com.apointmentManagementSystem.entity.AppointmentHistory;
import com.apointmentManagementSystem.entity.User;
import com.apointmentManagementSystem.entity.UserAppointment;
import com.apointmentManagementSystem.enumEntity.AppointmentResponse;
import com.apointmentManagementSystem.exception.ResourceAlreadyExistException;
import com.apointmentManagementSystem.exception.ResourceNotFoundException;
import com.apointmentManagementSystem.repository.IAppointmentHistoryRepository;
import com.apointmentManagementSystem.repository.IAppointmentRepository;
import com.apointmentManagementSystem.repository.IBlockedUserRepository;
import com.apointmentManagementSystem.repository.IUserAppointmentRepository;
import com.apointmentManagementSystem.repository.IUserRepository;
import com.apointmentManagementSystem.service.AppointmentService;
import com.apointmentManagementSystem.utils.ErrorMessageConstant;

@Service
public class AppointmentServiceImpl implements AppointmentService{

	@Autowired
	private IAppointmentRepository appointmentRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IUserAppointmentRepository userAppointmentRepository;
	
	@Autowired
	private IBlockedUserRepository blockUserRepository;
	
	@Autowired
	private IAppointmentHistoryRepository appointmentHistoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public void createAppointment(AppointmentRequestDto appointmentRequest, int loggedUser) {
		
		User getManager = userRepository.findByIdAndIsActiveTrue(loggedUser)
										.orElseThrow();
		
		if(appointmentRepository.existsByAppointmentDateAndManagerIdAndIsActiveTrue(appointmentRequest.getAppointmentDate(), loggedUser)) {
			throw new ResourceAlreadyExistException(ErrorMessageConstant.APPOINTMENT_ALREADY_EXIST);
		}
		
		List<User> getDevelopers = userRepository.findByIdInAndRoleNameAndIsActiveTrue(appointmentRequest.getDeveloper(), "DEVELOPER");
		
		if(getDevelopers.size() != appointmentRequest.getDeveloper().size()) {
			throw new ResourceNotFoundException(ErrorMessageConstant.USER_NOT_EXIST);
		}
		
		AppointmentEntity newAppointment = new AppointmentEntity();
		newAppointment.setAppointmentDate(appointmentRequest.getAppointmentDate());
		newAppointment.setCreatedBy(loggedUser);
		newAppointment.setManager(getManager);
		
		 AppointmentEntity createdAppointment = appointmentRepository.save(newAppointment);
		 
		 List<UserAppointment> userAppointment= getDevelopers.stream()
				 											 .filter(developer -> !blockUserRepository.existsByBlockedByIdAndBlockedToIdAndIsActiveTrue(developer.getId(), loggedUser))
				 											 .map(developer -> new UserAppointment(AppointmentResponse.PENDDING, developer, createdAppointment)).toList();
		 
		 List<UserAppointment> createdUserAppointment =   userAppointmentRepository.saveAll(userAppointment);
		 
		 List<AppointmentHistory> appointmentHistory =  createdUserAppointment.stream().map(newUserAppointment -> {
			 
			 						AppointmentHistory history = new AppointmentHistory();
									 history.setResponse(newUserAppointment.getResponse());
									 history.setUserAppointment(newUserAppointment);
									 history.setCreatedBy(loggedUser);
									 
									 return history;
									}).toList();
		 
		 appointmentHistoryRepository.saveAll(appointmentHistory);
		 
		 
	}

	@Override
	public void deleteAppointment(int id, int loggedUser) {
		
		appointmentRepository.findByIdAndManagerIdAndIsActiveTrue(id , loggedUser)
							 .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.APPOINTMENT_NOT_EXIST));
		
		appointmentRepository.deleteById(id, loggedUser);
		userAppointmentRepository.deleteByAppointmentId(id, loggedUser);
		
	}


	@Override
	public AppointmentResponseDto getAppointmentById(int id) {
		
		AppointmentEntity appointment =  appointmentRepository.findByIdAndIsActiveTrue(id).orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.APPOINTMENT_NOT_EXIST));
		
		return modelMapper.map(appointment, AppointmentResponseDto.class)	;
		
	}

	@Override
	public List<?> getAllAppointment(LocalDate fromDate , LocalDate toDate , int loggedInUser) {
		User getUser = userRepository.findByIdAndIsActiveTrue(loggedInUser).orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.USER_NOT_EXIST));
		
		if(toDate == null) {
			toDate = LocalDate.now();
		}
		if(fromDate == null) {
			fromDate = LocalDate.now().minusMonths(3);
		}
		
		if(getUser.getRole().getName().equals("MANAGER")) {
		return appointmentRepository.findAllByManagerIdAndIsActiveTrue(loggedInUser);
		}
		else if(getUser.getRole().getName().equals("DEVELOPER")) {
			
			return appointmentRepository.getAllApointmentOfUser(fromDate, toDate.plusDays(1), loggedInUser);
		}
		
		return null;
	}

	@Override
	public void updateApointment(int id, int loggedUser) {
		
	}


	

}
