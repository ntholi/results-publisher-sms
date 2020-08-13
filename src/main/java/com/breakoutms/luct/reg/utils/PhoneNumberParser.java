package com.breakoutms.luct.reg.utils;

public interface PhoneNumberParser {

	public static String parseNumber(String phoneNumber) {
		String result = null;
		if(phoneNumber == null){
			System.err.println("Phone number is null!");
			return phoneNumber;
		}
		if(phoneNumber.length() == 8){
			if(phoneNumber.startsWith("5")){
				result = "266"+phoneNumber;
			}
			else if(phoneNumber.startsWith("6")){
				result = "266"+phoneNumber;
			}
		}
		else if(phoneNumber.length() == 10 && 
				phoneNumber.startsWith("0")){
			result = "27"+phoneNumber.substring(1);
		}
		else if(phoneNumber.startsWith("+")){
			if(phoneNumber.length() == 12 
					&& phoneNumber.startsWith("+266")){
				result = phoneNumber.substring(1);
			}
			if(phoneNumber.length() == 12 
					&& phoneNumber.startsWith("+27")){
				result = phoneNumber.substring(1);
			}
		}
		else if(phoneNumber.length() == 11 
				&& (phoneNumber.startsWith("27") || 
						phoneNumber.startsWith("266"))){
			result = phoneNumber;
		}
		return result;
	}

	public static boolean isValid(String phoneNumber) {
		return parseNumber(phoneNumber) != null;
	}
}
