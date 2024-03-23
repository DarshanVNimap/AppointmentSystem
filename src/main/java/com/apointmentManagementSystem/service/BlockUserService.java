package com.apointmentManagementSystem.service;

import java.util.List;

import com.apointmentManagementSystem.dto.BlockUserDto;
import com.apointmentManagementSystem.dto.BlockedUserResponseDto;

public interface BlockUserService {
	
	void blockUser(BlockUserDto blockUser , int loggedInUser);
	List<BlockedUserResponseDto> getBlockListOfUser(int loggedInUser);
	

}
