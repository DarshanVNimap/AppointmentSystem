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
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Role role;
	
	@OneToMany(cascade = CascadeType.ALL , mappedBy = "manager" )
	private List<AppointmentEntity> managerAppointment;
	
	@OneToMany(cascade = CascadeType.ALL , mappedBy = "developer")
	private List<UserAppointment> developer;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "blockedBy")
	private List<BlockedUser> blockedBy;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "blockedTo")
	private List<BlockedUser> blockedTo;
	
	private boolean isActive = true;
		
	private int createdBy;
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	private int updatedBy;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@Override
	public String toString() {
		return "[id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + "]";
	}
	
	 

	
}
