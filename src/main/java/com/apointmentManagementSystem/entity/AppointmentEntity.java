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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "appointment")
public class AppointmentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private LocalDateTime appointmentDate;
	
	@OneToMany(cascade = CascadeType.ALL , mappedBy = "appointment")
	private List<UserAppointment> appointment;
	
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	private User manager;
	
	
	private boolean isActive = true;
	private int createdBy;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	private int updatedBy;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;

}
