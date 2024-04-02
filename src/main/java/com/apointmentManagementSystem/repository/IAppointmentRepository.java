package com.apointmentManagementSystem.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.apointmentManagementSystem.IDtos.AppointmentResponseIDto;
import com.apointmentManagementSystem.IDtos.UserAppointmentResponseIDto;
import com.apointmentManagementSystem.entity.AppointmentEntity;

import jakarta.transaction.Transactional;

public interface IAppointmentRepository extends JpaRepository<AppointmentEntity, Integer> {
	
	boolean existsByAppointmentDateAndManagerIdAndIsActiveTrue(LocalDateTime appointmentDate , int managerId);
	
	Optional<AppointmentEntity> findByIdAndManagerIdAndIsActiveTrue(int id , int managerId);
	
	List<AppointmentResponseIDto> findAllByManagerIdAndIsActiveTrue(int loggedInUserId);
	
	Optional<AppointmentEntity> findByIdAndIsActiveTrue(int id);
	
	@Query(value = "SELECT\r\n"
			+ "    ap.id AS appointmentId,\r\n"
			+ "    DATE(ap.appointment_date) AS appointmentDate,\r\n"
			+ "    CAST(ap.appointment_date AS TIME) AS appointmentTime,\r\n"
			+ "    u_manager.id AS managerId,"
			+ "	   CONCAT(u_manager.first_name , ' ', u_manager.last_name) AS manager\r\n"
			+ "FROM\r\n"
			+ "    users u\r\n"
			+ "LEFT JOIN appointment ap ON u.id = ap.manager_id\r\n"
			+ "LEFT JOIN user_appointment up ON up.appointment_id = ap.id\r\n"
			+ "LEFT JOIN users u_developer ON up.developer_id = u_developer.id\r\n"
			+ "INNER JOIN users u_manager ON u_manager.id = ap.manager_id\r\n"
			+ "WHERE\r\n"
			+ "    (\r\n"
			+ "        (:fromDate = '' OR ap.appointment_date >= :fromDate)\r\n"
			+ "        AND (:toDate = '' OR ap.appointment_date <= :toDate)\r\n"
			+ "        AND u_developer.id = :userId\r\n"
			+ "    )\r\n"
			+ "    OR (:fromDate = '' AND :toDate= '' AND u_developer.id = :userId)\r\n"
			+ "GROUP BY\r\n"
			+ "    ap.id, u_manager.id, u_manager.first_name, u_manager.last_name;" , nativeQuery = true)
	List<UserAppointmentResponseIDto> getAllApointmentOfUser(LocalDate fromDate , LocalDate toDate , int userId);
	
	@Transactional
	@Modifying
	@Query(value = "update appointment set is_active = false , updated_by =:loggedInUser where id =:id" , nativeQuery = true)
	void deleteById(int id , int loggedInUser);

}
