package com.breakoutms.luct.reg.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.breakoutms.luct.reg.model.beans.Marks;
import com.breakoutms.luct.reg.model.beans.PhoneNumber;
import com.breakoutms.luct.reg.model.beans.Student;
import com.breakoutms.luct.reg.model.beans.StudentResult;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class SheetReader {

	private DataFormatter dataFormatter = new DataFormatter();
	private Sheet sheet;
	private Map<Integer, String> courseNames;
	private int namesCol;
	private int contactsCol;
	private int studentNumberCol;
	private int remarksCol;
	private int firstRow;
	
	public SheetReader(String workbookName, Sheet sheet) {
		this.sheet = sheet;
		firstRow = SheetHelper.getFirstRow(workbookName, sheet);
		namesCol = SheetHelper.getNamesCol(workbookName, sheet);
		contactsCol = SheetHelper.getContactsCol(workbookName, sheet);
		studentNumberCol = SheetHelper.getStudentNumberCol(workbookName, sheet);
		courseNames = SheetHelper.getCourseNames(sheet, firstRow);
		remarksCol = SheetHelper.getRemarksCol(workbookName, sheet);
	}
	
	public Set<StudentResult> getResults() {
		Set<StudentResult> list = new HashSet<>();
		sheet.forEach(row -> {
			if(row.getRowNum() >= firstRow) {
				StudentResult std = readStudentResults(row);
				list.add(std);
			}
		});
		return list;
	}
	
	private StudentResult readStudentResults(Row row) {
		Student std = getStudent(row);
		if(std == null) {
			return null;
		}
		StudentResult result = new StudentResult();
		result.setStudent(std);
		
		Set<Marks> marks = new HashSet<>();
		for (Map.Entry<Integer, String> entry : courseNames.entrySet()) {
		    Integer cellnum = entry.getKey()+1;
		    String course = entry.getValue();
		    Cell gradeCell = row.getCell(cellnum);
		    if(gradeCell != null) {
		    	String grade = dataFormatter.formatCellValue(gradeCell);
		    	if(StringUtils.isNotBlank(grade)) {
		    		marks.add(new Marks(course.strip(), grade.strip()));
		    	}
		    }
		}
		result.setMarks(marks);
		
		Cell remarkCell = row.getCell(remarksCol);
		if(remarkCell != null) {
			String remarks = dataFormatter.formatCellValue(remarkCell);
	    	if(StringUtils.isNotBlank(remarks)) {
	    		result.setRemarks(remarks.strip());
	    	}
		}
		
		return result;
	}
	
	private Student getStudent(Row row) {
		Student std = new Student();
		for (Cell cell : row) {
			int col = cell.getColumnIndex();
			String cellValue = dataFormatter.formatCellValue(cell);
			if(col == namesCol) {
				std.setNames(cellValue);
			}
			if(col == contactsCol) {
				std.setPhoneNumber(new PhoneNumber(cellValue));
			}
			if(col == studentNumberCol) {
				std.setStudentNumber(cellValue);
			}
		}
		if(StringUtils.isBlank(std.getStudentNumber()) || 
				!StringUtils.isNumeric(std.getStudentNumber())) {
			return null;
		}
		return std;
	}
}
