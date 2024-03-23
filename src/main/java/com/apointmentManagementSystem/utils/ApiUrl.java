package com.apointmentManagementSystem.utils;

public class ApiUrl {

	public static final String API = "/api";
	
	public static final String AUTH = "/auth";
	public static final String LOGIN = "/login";
	public static final String REFRESH_TOKEN = "/refresh-token";
	public static final String FORGOT_PASSWORD_CONFIRM = "/reset-password";
	public static final String FORGOT_PASSWORD = "/forgot-password";
	public static final String OTP_VERIFICATION="/otp-verify";
	
	public static final String USERS = "/users";
	
	public static final String[] URLS_WITHOUT_HEADER = {
			API + AUTH + LOGIN ,  API + AUTH + FORGOT_PASSWORD_CONFIRM ,
			API + AUTH + FORGOT_PASSWORD , API + AUTH + OTP_VERIFICATION
	};
	
}
