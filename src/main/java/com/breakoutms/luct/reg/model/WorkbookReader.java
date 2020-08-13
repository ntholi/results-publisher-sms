package com.breakoutms.luct.reg.model;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.breakoutms.luct.reg.model.beans.StudentResult;

public class WorkbookReader {

	public Set<StudentResult>  read(List<File> files) {
		Set<StudentResult> list = new HashSet<>();
		for (File file : files) {
			try (Workbook workbook = WorkbookFactory.create(file)){
				var bookReader = new WorkbookReader();
				list.addAll(bookReader.read(workbook));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

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
