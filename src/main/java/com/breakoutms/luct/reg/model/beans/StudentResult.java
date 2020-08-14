package com.breakoutms.luct.reg.model.beans;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class StudentResult {
	
	private Student student;
	private Set<Marks> marks = new HashSet<>();
	private String remarks;
	
	@Override
	public String toString() {
		return student.getStudentNumber() +": "+marks.toString() +" -> "+ remarks;
	}
	
	public SMS getSMS() {
		SMS sms = new SMS();
		sms.setId(getStudentNumber());
		sms.setMessage(getMessage());
		sms.setPhoneNumber(getPhoneNumber());
		double size = sms.getMessage().length() / 160.0;
		sms.setNumberOfSMSs((int) Math.ceil(size));
		return sms;
	}

	public String getStudentNumber() {
		if(student != null) {
			return student.getStudentNumber();
		}
		return null;
	}
	
	public String getPhoneNumber() {
		if(student != null) {
			return student.getPhoneNumber().getValue();
		}
		return null;
	}
	
	public String getMessage() {
		StringBuilder sb = new StringBuilder("Results for ");
		sb.append(getStudentNumber());
		for (Marks mark : marks) {
			sb.append("\n").append(mark.toString());
		}
		sb.append("\n").append(remarks);
		return sb.toString();
	}
}
