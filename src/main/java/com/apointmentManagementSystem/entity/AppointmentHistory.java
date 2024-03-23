package com.apointmentManagementSystem.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.apointmentManagementSystem.enumEntity.AppointmentResponse;

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
public class AppointmentHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private AppointmentResponse response;
	private boolean isActive = true;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private UserAppointment userAppointment;
	
	private int createdBy;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	

}
