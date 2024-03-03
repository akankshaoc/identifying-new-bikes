package utils;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import common.AppTest;

public class CaptureScreenshot extends AppTest{
	@Override
	public void initialise() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void naturalise() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public static String save(String name) throws Exception {
		
		String fileName = 
				"./src/test/resources/screenshots/"
				+ name
				+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH mm ss]"))
				+ ".png";
		File img = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		img.renameTo(new File(fileName));
		return fileName;
	}
}
