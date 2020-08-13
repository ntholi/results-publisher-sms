package com.breakoutms.luct.reg.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.breakoutms.luct.reg.model.beans.StudentResult;

public class WorkbookReader {
	
	public Set<StudentResult> read(Workbook workbook) {
		Set<StudentResult> studentResults = new HashSet<>();
		workbook.forEach(it ->{
			var res = readSheet(it);
			studentResults.addAll(res);
		});
		return studentResults;
	}

	private Set<StudentResult> readSheet(Sheet sheet) {
		SheetReader sheetReader = new SheetReader(sheet);
		return sheetReader.getResults();
	}
}
