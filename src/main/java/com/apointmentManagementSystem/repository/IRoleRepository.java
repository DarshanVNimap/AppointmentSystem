package com.apointmentManagementSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.apointmentManagementSystem.entity.Role;

public interface IRoleRepository extends JpaRepository<Role, Integer> {

	boolean existsByNameIgnoreCaseAndIsActiveTrue(String roleName);
	
	@Query(value = "update role set is_active=false , updated_by=?2 where id=?1", nativeQuery = true)
	void deleteRoleById(int roleId , int loggedUser);
	
	Optional<Role> findByIdAndIsActiveTrue(int roleId);
	
	List<Role> findAllByIsActiveTrue();

}
