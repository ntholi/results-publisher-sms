package com.breakoutms.luct.reg.model;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public interface SheetHelper {

	public static final String ERROR_MESSAGE = "Unable to find a required cell on sheet '%s' in '%s', "
			+ "was expecting a cell with a value matching any of the following '%s'";
	
	public static int getLastCol(String workbookName, Sheet sheet) {
		String indicator = "No. of Module";
		DataFormatter dataFormatter = new DataFormatter();
		for (Row rows : sheet) {
			for (Cell cell : rows) {
				String cellValue = dataFormatter.formatCellValue(cell);
				if(cellValue.toUpperCase().contains(indicator.toUpperCase())) {
					return cell.getColumnIndex() - 3;
				}
			}	
		}
		throw new IllegalStateException(String.format(ERROR_MESSAGE, 
				sheet.getSheetName(), workbookName, indicator));
	}

	public static int getFirstCol(String workbookName, Sheet sheet) {
		return getFirstCell(workbookName, sheet).getColumnIndex() - 3;
	}

	public static int getFirstRow(String workbookName, Sheet sheet) {
		return getFirstCell(workbookName, sheet).getRowIndex() + 1;
	}

	private static Cell getFirstCell(String workbookName, Sheet sheet) {
		String indicator = "Mk";
		DataFormatter dataFormatter = new DataFormatter();
		for (Row rows : sheet) {
			for (Cell cell : rows) {
				String cellValue = dataFormatter.formatCellValue(cell);
				if(cellValue.toUpperCase().contains(indicator.toUpperCase())) {
					return cell;
				}
			}	
		}
		throw new IllegalStateException(String.format(ERROR_MESSAGE, 
				sheet.getSheetName(), workbookName, indicator));
	}
	
	public static int getRemarksCol(String workbookName, Sheet sheet) {
		return getStudentDetailsCol(workbookName, sheet, "Remark");
	}

	public static int getNamesCol(String workbookName, Sheet sheet) {
		return getStudentDetailsCol(workbookName, sheet, "Name");
	}

	public static int getStudentNumberCol(String workbookName, Sheet sheet) {
		return getStudentDetailsCol(workbookName, sheet, "Student N", "StudentN", "StudentID", "Student ID");

	}

	public static int getContactsCol(String workbookName, Sheet sheet) {
		return getStudentDetailsCol(workbookName, sheet, "Contact", "Phone");
	}

	public static Map<Integer, String> getCourseNames(Sheet sheet, int resultsRow) {
		DataFormatter dataFormatter = new DataFormatter();
		Map<Integer, String> courses = new HashedMap<>();
		for (Row row : sheet) {
			for (Cell cell : row) {
				if(cell.getRowIndex() > 3 
						&& cell.getColumnIndex() > 2 
						&& cell.getRowIndex() < resultsRow) {
					String cellValue = dataFormatter.formatCellValue(cell);
					if(isAlphanumeric(cellValue)) {
						courses.put(cell.getColumnIndex(), cellValue);
					}
				}
			}
		}

		return courses;
	}

	public static int getStudentDetailsCol(String workbookName, Sheet sheet, String... indicators) {
		DataFormatter dataFormatter = new DataFormatter();
		for (Row rows : sheet) {
			for (Cell cell : rows) {
				String cellValue = dataFormatter.formatCellValue(cell);
				if(contains(cellValue, indicators)) {
					return cell.getColumnIndex();
				}
			}	
		}
		throw new IllegalStateException(String.format(ERROR_MESSAGE, 
				sheet.getSheetName(), workbookName, Arrays.toString(indicators)));
	}

	private static boolean isAlphanumeric(String str) {
		if(str == null || str.isBlank()) {
			return false;
		}
		boolean digit = false;
		boolean letter = false;
		for (Character ch : str.toCharArray()) {
			if(Character.isDigit(ch)) {
				digit = true;
			}
			if(Character.isLetter(ch)) {
				letter = true;
			}
		}
		return letter && digit;
	}

	private static boolean contains(String cellValue, String[] indicators) {
		for (String str : indicators) {
			if(cellValue.toUpperCase().contains(str.toUpperCase())) {
				return true;
			}
		}
		return false;
	}
}
