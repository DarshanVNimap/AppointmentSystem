package com.apointmentManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.apointmentManagementSystem.entity.RolePermission;

import jakarta.transaction.Transactional;

public interface IRolePermissionRepository extends JpaRepository<RolePermission, Integer> {

	List<RolePermission> findAllByRoleIdAndIsActiveTrue(int roleId);

	@Modifying
	@Query(value = "update role_permission rp set is_active=false , updated_by=:loggedUser where rp.role_id=:roleId", nativeQuery = true)
	void deleteByRoleId(int roleId, int loggedUser);

	@Transactional
	@Modifying
	@Query(value = "update role_permission rp set is_active=false , updated_by=:loggedUser  where rp.permission_id=:id", nativeQuery = true)
	void deleteByPermissionId(@Param("id") int permissionId, int loggedUser);

	@Transactional
	@Modifying
	@Query(value = "update role_permission rp set is_active=false , updated_by=:userId where rp.permission_id=:permissionId and rp.role_id=:roleId", nativeQuery = true)
	void deleteByroleIdAndPermissionId(int roleId, int permissionId, int userId);

	boolean existsByRoleIdAndPermissionIdAndIsActiveTrue(int roleId, int permissionId);

}
