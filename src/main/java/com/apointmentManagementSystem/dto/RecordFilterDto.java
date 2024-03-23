package com.apointmentManagementSystem.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecordFilterDto {
	
	LocalDate startDate;
	
	LocalDate endDate;

}
