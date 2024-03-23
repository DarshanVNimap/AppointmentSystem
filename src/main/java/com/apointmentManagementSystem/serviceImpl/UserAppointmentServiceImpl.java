package com.apointmentManagementSystem.serviceImpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apointmentManagementSystem.IDtos.RecordFilterResponseDto;
import com.apointmentManagementSystem.entity.AppointmentHistory;
import com.apointmentManagementSystem.entity.UserAppointment;
import com.apointmentManagementSystem.enumEntity.AppointmentResponse;
import com.apointmentManagementSystem.exception.ResourceNotFoundException;
import com.apointmentManagementSystem.repository.IAppointmentHistoryRepository;
import com.apointmentManagementSystem.repository.IUserAppointmentRepository;
import com.apointmentManagementSystem.service.UserAppointmentService;
import com.apointmentManagementSystem.utils.ErrorMessageConstant;

@Service
public class UserAppointmentServiceImpl implements UserAppointmentService {
	
	@Autowired
	private IUserAppointmentRepository userAppointmentRepository;
	
	@Autowired
	private IAppointmentHistoryRepository appointmentHistoryRepository;

	@Override
	public void updateAppointmentResponse(int appointmentId, AppointmentResponse response, int loggedInUser) {
		
		UserAppointment getUserAppointment = userAppointmentRepository.findByAppointmentIdAndDeveloperIdAndIsActiveTrue(appointmentId, loggedInUser).orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.APPOINTMENT_NOT_EXIST));
		
		getUserAppointment.setResponse(response);
		
		UserAppointment updatedAppointment =  userAppointmentRepository.save(getUserAppointment);
		
		AppointmentHistory history = new AppointmentHistory();
		 history.setResponse(updatedAppointment.getResponse());
		 history.setUserAppointment(updatedAppointment);
		 history.setCreatedBy(loggedInUser);
		 
		 appointmentHistoryRepository.save(history);
		
		
	}
	
	public RecordFilterResponseDto getMeetingRecord(LocalDate startDate , LocalDate endDate , int loggedInUser){
		
		RecordFilterResponseDto filterRecord = userAppointmentRepository.getFilterRecord(startDate, endDate, loggedInUser , RecordFilterResponseDto.class);
		return filterRecord;
	}

}
