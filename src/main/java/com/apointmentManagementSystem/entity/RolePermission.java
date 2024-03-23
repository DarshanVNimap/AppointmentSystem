package com.apointmentManagementSystem.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class RolePermission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Role role;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Permission permission;
	
	private boolean isActive = true;
	
	private int createdBy;

	@CreationTimestamp
	private LocalDateTime createdAt;
	
	private int updatedBy;

	@UpdateTimestamp
	private LocalDateTime updatedAt;


}
