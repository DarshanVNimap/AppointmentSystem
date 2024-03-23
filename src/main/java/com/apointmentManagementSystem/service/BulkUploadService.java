package com.apointmentManagementSystem.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface BulkUploadService {
	
	void importUserFromExcel(MultipartFile file , int loggedInUser) throws Exception;
	byte[] downloadFile(String filename  , int loggedInUser) throws IOException, Exception;

}
