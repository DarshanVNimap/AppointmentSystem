package com.apointmentManagementSystem.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL , mappedBy = "role")
	private List<User> users;
	
	@OneToMany(cascade = CascadeType.ALL , mappedBy = "role")
	private List<RolePermission> rolePermission;
	
	private boolean isActive = true;
	
	private int createdBy;

	@CreationTimestamp
	private LocalDateTime createdAt;
	
	private int updatedBy;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

}
 