package com.apointmentManagementSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.apointmentManagementSystem.entity.Permission;

import jakarta.transaction.Transactional;

public interface IPermissionRepository extends JpaRepository<Permission, Integer> {

	boolean existsByActionIgnoreCaseAndIsActiveTrue(String name);

	List<Permission> findByIdInAndIsActiveTrue(List<Integer> ids);

	List<Permission> findAllByIsActiveTrue();
	
	@Modifying
	@Transactional
	@Query(value = "update permission set is_active=false , updated_by=:loggedUser where id=:id", nativeQuery = true)
	void deleteById(@Param("id") int id , @Param("loggedUser") int userId);
	
	Optional<Permission> findByActionAndIsActiveTrue(String actionName);
	
	Optional<Permission> findByIdAndIsActiveTrue(int permissionId);

	

}
