package com.apointmentManagementSystem.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validator {
	
	
	private static final String EMAIL_PATTERN = "^(?=\\S+@\\S+\\.\\S+$)(?=.*@([a-zA-Z0-9-]+\\.)+[a-zA-Z0-9-]+$).+$";
	private static final Pattern EMAIL = Pattern.compile(EMAIL_PATTERN);

	public static boolean isValidforEmail(final String email) {
		Matcher matcher = EMAIL.matcher(email);
		return matcher.matches();
	}


}
