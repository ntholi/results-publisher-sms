package com.breakoutms.luct.reg.model;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public interface SheetHelper {

	public static final String ERROR_MESSAGE = "Unable to find a required cell on sheet '%s', "
			+ "was expecting a cell with a value matching '%s'";
	
	public static int getLastCol(Sheet sheet) {
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
				sheet.getSheetName(), indicator));
	}

	public static int getFirstCol(Sheet sheet) {
		return getFirstCell(sheet).getColumnIndex() - 3;
	}

	public static int getFirstRow(Sheet sheet) {
		return getFirstCell(sheet).getRowIndex() + 1;
	}

	private static Cell getFirstCell(Sheet sheet) {
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
				sheet.getSheetName(), indicator));
	}
	
	public static int getRemarksCol(Sheet sheet) {
		return getStudentDetailsCol(sheet, "Remark");
	}

	public static int getNamesCol(Sheet sheet) {
		return getStudentDetailsCol(sheet, "Name");
	}

	public static int getStudentNumberCol(Sheet sheet) {
		return getStudentDetailsCol(sheet, "Student N", "StudentN", "StudentID", "Student ID");

	}

	public static int getContactsCol(Sheet sheet) {
		return getStudentDetailsCol(sheet, "Contact", "Phone");
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

	public static int getStudentDetailsCol(Sheet sheet, String... indicators) {
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
				sheet.getSheetName(), Arrays.toString(indicators)));
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
