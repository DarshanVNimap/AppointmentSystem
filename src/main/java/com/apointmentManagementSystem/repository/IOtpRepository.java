package com.apointmentManagementSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.apointmentManagementSystem.entity.OtpEntity;

import jakarta.transaction.Transactional;

public interface IOtpRepository extends JpaRepository<OtpEntity, Integer> {
	
	@Transactional
	@Modifying
	@Query(value = "delete from otp_entity where email=?1" , nativeQuery = true)
	void deleteAllByEmail(String email);
	
	Optional<OtpEntity> findByEmailIgnoreCaseAndOtp(String email , String otp);

}
