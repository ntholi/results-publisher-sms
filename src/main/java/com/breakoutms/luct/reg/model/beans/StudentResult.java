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
}
