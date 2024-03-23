package com.apointmentManagementSystem.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.apointmentManagementSystem.enumEntity.AppointmentResponse;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class UserAppointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private AppointmentResponse response;
	
	@ManyToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	private User  developer;
	
	@ManyToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	private AppointmentEntity appointment;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userAppointment")
	private List<AppointmentHistory> appointmentHistory;

	private boolean isActive = true;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	private int updatedBy;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public UserAppointment(AppointmentResponse response, User developer, AppointmentEntity appointment) {
		super();
		this.response = response;
		this.developer = developer;
		this.appointment = appointment;
	}
	
	

}
