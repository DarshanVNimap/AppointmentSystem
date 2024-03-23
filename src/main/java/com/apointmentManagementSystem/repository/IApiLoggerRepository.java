package com.apointmentManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apointmentManagementSystem.entity.ApiLoggerEntity;

public interface IApiLoggerRepository extends JpaRepository<ApiLoggerEntity, Integer> {

}
