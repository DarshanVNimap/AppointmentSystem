package com.apointmentManagementSystem.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.apointmentManagementSystem.IDtos.RecordFilterResponseDto;
import com.apointmentManagementSystem.entity.UserAppointment;

import jakarta.transaction.Transactional;

public interface IUserAppointmentRepository extends JpaRepository<UserAppointment, Integer> {
	
	Optional<UserAppointment> findByAppointmentIdAndDeveloperIdAndIsActiveTrue(int appointmentId , int developerId);
	
	
	@Transactional
	@Modifying
	@Query(value = "update user_appointment set is_active = false , updated_by =:loggedInUser where appointment_id =:id" , nativeQuery = true)
	void deleteByAppointmentId(int id , int loggedInUser);

	@Query(value = "SELECT  * FROM get_meeting_reports(:startDate, :endDate , :developerId)" , nativeQuery = true)
	RecordFilterResponseDto getFilterRecord(@Param("startDate") LocalDate startDate , @Param("endDate") LocalDate endDate ,@Param("developerId") int developerId , Class<RecordFilterResponseDto> class1);
}
