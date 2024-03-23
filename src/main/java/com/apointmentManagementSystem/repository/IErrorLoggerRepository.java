package com.apointmentManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apointmentManagementSystem.entity.ErrorLogger;

public interface IErrorLoggerRepository extends JpaRepository<ErrorLogger, Integer>{

}
