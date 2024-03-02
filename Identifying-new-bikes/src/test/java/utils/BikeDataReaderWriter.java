package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class BikeDataReaderWriter {
	private String filePath = "./src/test/resources/test_data/new_bikes_test_data.xlsx";
	private FileInputStream fileInputStream;
	private FileOutputStream fileOutputStream;
	private XSSFWorkbook workbook;
	private XSSFSheet newBikesSheet;

	public void writeToNewBikes(String[] headers, List<String[]> data) throws IOException {
		workbook = new XSSFWorkbook();
		newBikesSheet = workbook.createSheet("New Bikes");
		// 1. write headers
		XSSFRow headerRow = newBikesSheet.createRow(0);
		for (int j = 0; j < headers.length; j++) {
			headerRow.createCell(j).setCellValue(headers[j]);
		}

		// 2. write data
		for (int rowIndex = 1; rowIndex < data.size() + 1; rowIndex++) {
			String rowData[] = data.get(rowIndex - 1);
			XSSFRow row = newBikesSheet.createRow(rowIndex);
			for (int colIndex = 0; colIndex < rowData.length; colIndex++) {
				row.createCell(colIndex).setCellValue(rowData[colIndex]);
			}
		}
		fileOutputStream = new FileOutputStream(filePath);
		//3. write edited data
		workbook.write(fileOutputStream);
		this.workbook.close();
		this.fileOutputStream.close();
	}

	public String[][] readFromNewBikes() throws IOException {
		fileInputStream = new FileInputStream(filePath);
		workbook = new XSSFWorkbook(fileInputStream);
		newBikesSheet = workbook.getSheet("New Bikes");
		// 1. return null if no data present
		if (newBikesSheet.getLastRowNum() < 1)
			return null;

		// 2. Initialize result array
		String[][] res = new String[newBikesSheet.getLastRowNum() - 1][newBikesSheet.getRow(0).getLastCellNum()];

		// 3. reading and storing data
		for (int rowIndex = 1; rowIndex < newBikesSheet.getLastRowNum(); rowIndex++) {
			XSSFRow row = newBikesSheet.getRow(rowIndex);
			for (int colIndex = 0; colIndex < row.getLastCellNum(); colIndex++) {
				res[rowIndex - 1][colIndex] = row.getCell(colIndex).getStringCellValue();
			}
		}
		this.workbook.close();
		this.fileInputStream.close();
		//4. return read data
		return res;
	}
}
