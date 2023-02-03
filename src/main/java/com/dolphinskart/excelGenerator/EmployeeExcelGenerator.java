package com.dolphinskart.excelGenerator;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.dolphinskart.entity.Employee;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class EmployeeExcelGenerator {
	private List<Employee> listEmp;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	public EmployeeExcelGenerator(List<Employee> listEmp) {
		this.listEmp = listEmp;
		workbook = new XSSFWorkbook();
	}

	private void writeHeader() {
		sheet = workbook.createSheet("Employee");

		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		createCell(row, 0, "empId", style);
		createCell(row, 1, "empName", style);
		createCell(row, 2, "empAge", style);
		createCell(row, 3, "empDept", style);
		createCell(row, 4, "empSalary", style);

	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((int) value);
		} else if (value instanceof String) {
			cell.setCellValue((String) value);
		} else if (value instanceof Integer) {
			cell.setCellValue((int) value);
		}
		else if (value instanceof String) {
			cell.setCellValue((String) value);
		}else {
			cell.setCellValue((Double) value);
		}
		cell.setCellStyle(style);
	}

	private void write() {
		int rowCount = 1;

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		for (Employee record : listEmp) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;

			createCell(row, columnCount++, record.getEmpId(), style);
			createCell(row, columnCount++, record.getEmpName(), style);
			createCell(row, columnCount++, record.getEmpAge(), style);
			createCell(row, columnCount++, record.getEmpDept(), style);
			createCell(row, columnCount++, record.getEmpSalary(), style);
		}
	}

	public void generate(HttpServletResponse response) throws IOException {
		writeHeader();
		write();
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}
}
