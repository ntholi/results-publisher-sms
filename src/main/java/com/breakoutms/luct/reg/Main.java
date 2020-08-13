package com.breakoutms.luct.reg;

import java.io.File;
import java.util.Set;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.breakoutms.luct.reg.model.WorkbookReader;
import com.breakoutms.luct.reg.model.beans.StudentResult;

public class Main {

	public static final String SAMPLE_XLSX_FILE_PATH = "/home/ntholi/Documents/Projects/Limkokwing/ResultsPublisher/sample.xlsx";

	public static void main(String[] args) throws Exception {

		Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));
	    System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");
	    var bookReader = new WorkbookReader();
	    Set<StudentResult> list = bookReader.read(workbook);
	    for (StudentResult res : list) {
			System.out.println(res);
		}
	}
}
