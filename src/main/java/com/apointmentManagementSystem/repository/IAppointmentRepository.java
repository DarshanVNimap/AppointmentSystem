package com.apointmentManagementSystem.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.apointmentManagementSystem.IDtos.AppointmentResponseIDto;
import com.apointmentManagementSystem.entity.AppointmentEntity;

import jakarta.transaction.Transactional;

public interface IAppointmentRepository extends JpaRepository<AppointmentEntity, Integer> {
	
	boolean existsByAppointmentDateAndManagerIdAndIsActiveTrue(LocalDateTime appointmentDate , int managerId);
	
	Optional<AppointmentEntity> findByIdAndManagerIdAndIsActiveTrue(int id , int managerId);
	
	List<AppointmentResponseIDto> findAllByIsActiveTrue();
	
	Optional<AppointmentEntity> findByIdAndIsActiveTrue(int id);
	
	@Transactional
	@Modifying
	@Query(value = "update appointment set is_active = false , updated_by =:loggedInUser where id =:id" , nativeQuery = true)
	void deleteById(int id , int loggedInUser);

}
