package com.apointmentManagementSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.apointmentManagementSystem.entity.User;

import jakarta.transaction.Transactional;

public interface IUserRepository extends JpaRepository<User, Integer> {
	
	boolean existsByIdAndRoleNameAndIsActiveTrue(int id , String role);
	
	Optional<User> findByEmailIgnoreCaseAndIsActiveTrue(String email);
	
	boolean existsByEmailIgnoreCaseAndIsActiveTrue(String email);
	
	Optional<User> findByIdAndIsActiveTrue(int id);
	
	List<User> findByIdInAndRoleNameAndIsActiveTrue(List<Integer> ids , String role);
	
	List<User> findAllByIsActiveTrue();
	
	@Transactional
	@Modifying
	@Query(value = "update users set is_active = false and updated_by =:loggedIn where id =:id" , nativeQuery = true)
	void deleteById(int id , int loggedIn);

}
