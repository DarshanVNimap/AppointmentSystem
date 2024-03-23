package com.apointmentManagementSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apointmentManagementSystem.entity.BulkUpload;

public interface IBulkUploadRepository extends JpaRepository<BulkUpload, Integer> {
	
	Optional<BulkUpload> findByFileNameAndUploadedBy(String fileName , int loggedInUser);

}
