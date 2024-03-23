package com.apointmentManagementSystem.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apointmentManagementSystem.dto.BlockUserDto;
import com.apointmentManagementSystem.dto.BlockedUserResponseDto;
import com.apointmentManagementSystem.entity.BlockedUser;
import com.apointmentManagementSystem.entity.User;
import com.apointmentManagementSystem.exception.ResourceNotFoundException;
import com.apointmentManagementSystem.repository.IBlockedUserRepository;
import com.apointmentManagementSystem.repository.IUserRepository;
import com.apointmentManagementSystem.service.BlockUserService;
import com.apointmentManagementSystem.utils.ErrorMessageConstant;

@Service
public class BlockUserServiceImpl implements BlockUserService{
	
	@Autowired
	private IBlockedUserRepository blockUserRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public void blockUser(BlockUserDto blockUser , int loggedInUser) {
		
		List<User> getManagers = userRepository.findByIdInAndRoleNameAndIsActiveTrue(blockUser.getBlockTo(), "MANAGER");
		User getDeveloper = userRepository.findByIdAndIsActiveTrue(loggedInUser).orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.USER_NOT_EXIST));
		
		if(getManagers.size() != blockUser.getBlockTo().size()) {
			throw new ResourceNotFoundException(ErrorMessageConstant.USER_NOT_EXIST);
		}
		
		List<BlockedUser> blockList =  getManagers.stream().map(manager -> {
										BlockedUser block = new BlockedUser();
										block.setBlockedTo(manager);
										block.setBlockedBy(getDeveloper);
										return block;
								 }).toList();
		
		blockUserRepository.saveAll(blockList);
		
	}


	@Override
	public List<BlockedUserResponseDto> getBlockListOfUser(int loggedInUser) {
		return blockUserRepository.findAllByBlockedByIdAndIsActiveTrue(loggedInUser).stream()
																				  .map(blockList -> modelMapper.map(blockList, BlockedUserResponseDto.class))
																				  .toList();
		
	}

}
