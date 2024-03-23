package com.apointmentManagementSystem.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.apointmentManagementSystem.entity.BulkUpload;
import com.apointmentManagementSystem.entity.Role;
import com.apointmentManagementSystem.entity.User;
import com.apointmentManagementSystem.exception.ResourceNotFoundException;
import com.apointmentManagementSystem.repository.IBulkUploadRepository;
import com.apointmentManagementSystem.repository.IRoleRepository;
import com.apointmentManagementSystem.utils.ErrorMessageConstant;
import com.apointmentManagementSystem.utils.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileServiceImpl {
	
	@Autowired
	private IBulkUploadRepository bulkUploadRepository;
	
	@Autowired
	private IRoleRepository roleRepository;

	static String[] HEADERS = { "FIRST NAME", "LAST NAME", "EMAIL", "ROLE" };

	private final String STORAGE_PATH = "C:\\Users\\Nimap\\Desktop\\AppointmentManagementSystem\\src\\main\\resources\\static\\upload\\" ;
	
//	public FileServiceImpl(@Value("${file.storage}") String STORAGE_PATH) throws IOException {
//		this.STORAGE_PATH = new ClassPathResource(STORAGE_PATH).getFile().getAbsolutePath();
//	}

//	public FileServiceImpl() {
//		this.STORAGE_PATH = "";
//		
//		// TODO Auto-generated constructor stub
//	}

	public static boolean checkUploadedFileType(MultipartFile file) {
		return file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	}

	public List<User> importExcel(MultipartFile file, int loggedInUser) throws Exception {

		List<User> report = new ArrayList<>();
		int errorCount = 0;
		int successCount = 0;
		List<Role> list = roleRepository.findAllByIsActiveTrue();
		
		InputStream is = file.getInputStream();
		
		uploadFile(file);

		try (XSSFWorkbook book = new XSSFWorkbook(is)) {
			// log.info(book.getSheetName(0));
			XSSFSheet sheet = book.getSheet("Sheet1");
			Iterator<Row> rows = sheet.iterator();

			int rowNumber = 0;
			rows.next();
			while (rows.hasNext()) {
//				if (rowNumber < 1) {
////					rows.next();
//					rowNumber++;
//					log.info("Row number: " + rowNumber);
//					continue;
//				}

				try {

					Iterator<Cell> cells = rows.next().iterator();
					Integer cellNumber = 0;

					User newUser = new User();

					while (cells.hasNext()) {

						Cell cell = cells.next();

						if (cellNumber < 3 && cell.getStringCellValue() == null) {
							throw new Exception(HEADERS[cellNumber] + " requeired..");
						}

						log.info("Row : " + rowNumber + " : " + cellNumber + " : " + cell.getCellType().toString());
						switch (cellNumber) {
						case 0:
							newUser.setFirstName(cell.getStringCellValue());

							break;
						case 1:
							newUser.setLastName(cell.getStringCellValue());

							break;
						case 2:
							if (!Validator.isValidforEmail(cell.getStringCellValue())) {
								throw new Exception("Invalid email");
							}
							newUser.setEmail(cell.getStringCellValue());

							break;
						case 3:
							Role role = list.get((int) cell.getNumericCellValue());
							if (role == null) {
								throw new ResourceNotFoundException(ErrorMessageConstant.ROLE_NOT_EXIST);
							}
							newUser.setRole(role);
							break;
						case 4:
							break;
						}
						cellNumber++;
					}
					newUser.setPassword(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10));
					newUser.setCreatedBy(loggedInUser);
					report.add(newUser);
					successCount++;

				} catch (Exception e) {
					errorCount++;
					log.error(e.getMessage());
				}
				rowNumber++;

			}
		}
		
		BulkUpload records = new BulkUpload();
		
		records.setFileName(file.getOriginalFilename());
		records.setErrorCount(errorCount);
		records.setSuccessCount(successCount);
		records.setUploadedBy(loggedInUser);
		
		bulkUploadRepository.save(records);
		
		return report;

	}

	public String uploadFile(MultipartFile file) throws IllegalStateException, IOException, Exception {

		log.info("Start uploading......");

		String filePath = STORAGE_PATH + file.getOriginalFilename();

		file.transferTo(new File(filePath));

		log.error("File couldn't uploaded");
		return "Somthing went wrong!!";
	}

	public byte[] downloadFile(String name, int loggedInUser) throws IOException, Exception {

		log.info("Start downloading...");

		BulkUpload getFile = bulkUploadRepository.findByFileNameAndUploadedBy(name , loggedInUser).orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.FILE_NOT_EXIST));
		String getPath = STORAGE_PATH+getFile.getFileName();
		log.info("File downloaded!");
		return Files.readAllBytes(new File(getPath).toPath());

	}

}
