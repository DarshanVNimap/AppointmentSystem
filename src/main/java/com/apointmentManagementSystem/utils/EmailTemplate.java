package com.apointmentManagementSystem.utils;

public class EmailTemplate {
	
	public static String INVITAIION_MAIL = "\r\n"
			+ "    <div style=\"max-width: 600px; margin: 0 auto; padding: 20px;\">\r\n"
			+ "        <h1 style=\"color: #333333; text-align: center;\">Invitation to Join Our Platform</h1>\r\n"
			+ "        <p>Hello [Username],</p>\r\n"
			+ "        <p>You have been invited to join our platform. Below are your login credentials:</p>\r\n"
			+ "        <ul style=\"list-style-type: none; padding-left: 0;\">\r\n"
			+ "            <li><strong>Username:</strong> [User's Email]</li>\r\n"
			+ "            <li><strong>Password:</strong> [User's Password]</li>\r\n"
			+ "        </ul>\r\n"
			+ "        <p style=\"text-align: center;\">If you have any questions, feel free to contact us at [Your contact email].</p>\r\n"
			+ "    </div>";
	
	public static String FORGOT_PASSWORD = "<div style=\"font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;\">\r\n"
			+ "        <p>Hello [USER NAME],</p>\r\n"
			+ "        <p>We received a request to reset your password. Please use the following One-Time Password (OTP) to reset your password:</p>\r\n"
			+ "        <h3 style=\"background-color: #f0f0f0; padding: 10px; border-radius: 5px;\">Your OTP: <span style=\"font-weight: bold; color: #4CAF50;\">[OTP]</span></h3>\r\n"
			+ "        <p>If you didn't request to reset your password, you can safely ignore this email. Your password will remain unchanged.</p>\r\n"
			+ "        <p>Thank you!</p>\r\n"
			+ "        <p>Best Regards,<br>Mr. Manager</p>\r\n"
			+ "    </div>";
	
	

}
