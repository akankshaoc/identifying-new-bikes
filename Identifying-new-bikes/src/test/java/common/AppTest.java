package common;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import page_objects.HomePage;
import utils.DriverSetup;

public class AppTest {
	protected WebDriver driver;
	protected HomePage homePage;
	
	@BeforeTest
	public void init() throws Exception {
		driver = DriverSetup.getDriver();
		homePage = new HomePage(driver);
		homePage.launch();
	}
	
	@AfterTest
	public void destroy() {
		driver.quit();
	}
}
