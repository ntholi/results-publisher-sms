package com.breakoutms.luct.reg.model.beans;

public class SMS {

	private String studentNumber;
	private String phoneNumber;
	private String message;
	private int numberOfSMSs;
	private boolean sent;
	private String errorMessage;
	
	public String getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getNumberOfSMSs() {
		return numberOfSMSs;
	}
	public void setNumberOfSMSs(int numberOfSMSs) {
		this.numberOfSMSs = numberOfSMSs;
	}
	public boolean isSent() {
		return sent;
	}
	public void setSent(boolean sent) {
		this.sent = sent;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
