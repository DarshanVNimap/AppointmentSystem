package com.apointmentManagementSystem.service;

import java.util.List;

import com.apointmentManagementSystem.dto.UpdateUserDto;
import com.apointmentManagementSystem.dto.UserRequestDto;
import com.apointmentManagementSystem.dto.UserResponseDto;

public interface UserService {
	
	void addUserDetail(UserRequestDto userRequest , int loggedUser);
	void updateUserDetail(UpdateUserDto userDto , int loggedUser);
	void deteleUser(int id , int loggedUser);
	UserResponseDto getUserById(int id , int loggedUser);
	List<UserResponseDto> getAllUserDetail();

}
