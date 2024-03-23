package com.apointmentManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apointmentManagementSystem.entity.BlockedUser;

public interface IBlockedUserRepository extends JpaRepository<BlockedUser, Integer> {
	
	boolean existsByBlockedByIdAndBlockedToIdAndIsActiveTrue(int blockedBy , int blockedTo);
	
	List<BlockedUser> findAllByBlockedByIdAndIsActiveTrue(int id);

}
