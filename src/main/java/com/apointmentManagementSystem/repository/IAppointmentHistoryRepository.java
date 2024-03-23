package com.apointmentManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apointmentManagementSystem.entity.AppointmentHistory;

public interface IAppointmentHistoryRepository  extends JpaRepository<AppointmentHistory, Integer>{

}
