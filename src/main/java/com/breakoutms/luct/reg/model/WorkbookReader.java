package com.breakoutms.luct.reg.model;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.breakoutms.luct.reg.model.beans.StudentResult;

import javafx.concurrent.Task;

public class WorkbookReader {

	private Map<String, Workbook> workBooks = new HashMap<>();
	private int sheetSize;

	public int loadFiles(List<File> files) {
		for (File file : files) {
			try {
				Workbook workbook = WorkbookFactory.create(file);
				workBooks.put(file.getName(), workbook);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		sheetSize = workBooks.values()
				.stream()
				.mapToInt(Workbook::getNumberOfSheets)
				.sum();
		return workBooks.size();
	}

	public Task<Set<StudentResult>> readStudentResults() {
		Task<Set<StudentResult>> task = new Task<>() {	
			@Override
			protected Set<StudentResult> call() throws Exception {
				Set<StudentResult> list = new HashSet<>();
				updateMessage("Loading student results...");
				double workDone = 1;
				for (var workbook : workBooks.entrySet()) {
					for (Sheet seet : workbook.getValue()) {
						updateMessage("Reading class: "+ seet.getSheetName());
						updateProgress(workDone, sheetSize);
						var res = readSheet(workbook.getKey(), seet);
						list.addAll(res);
						++workDone;
					}
				}
				return list;
			}
			
			@Override
			protected void failed() {
//				super.failed();
				updateProgress(0, 0);
				updateMessage(getException().getMessage());
			}
		};
		new Thread(task).start();
		return task;
	}

	private Set<StudentResult> readSheet(String workbook, Sheet sheet) {
		SheetReader sheetReader = new SheetReader(workbook, sheet);
		return sheetReader.getResults();
	}
}
