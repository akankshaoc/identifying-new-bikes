package common;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import page_objects.HomePage;
import utils.DriverSetup;

public abstract class AppTest {
	protected static WebDriver driver;
	protected static HomePage homePage;
	
	@BeforeSuite
	public void init() throws Exception {
		driver = DriverSetup.getDriver();
		homePage = new HomePage(driver);
		homePage.launch();
	}
	
	/**
	 * all classes extending AppTest must write initialization logic in this method
	 * use this method for the following
	 * <ul>
	 * <li> navigations to page you're testing on</li> 
	 * <li> initializing page objects</li> 
	 * <li> pre-requisites for performing tests</li> 
	 * </ul>
	 * @throws Exception 
	 */
	abstract public void initialise() throws Exception;
	
	/**
	 * all classes extending AppTest must write naturalization logic in this method
	 * use this method for the following
	 * <ul>
	 * <li> returning the application to pre test class running state</li> 
	 * <li> closing all resources </li> 
	 * </ul>
	 */
	abstract public void naturalise() throws Exception;
	
	@AfterSuite
	public void destroy() {
		driver.quit();
	}
}
