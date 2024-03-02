package test_scenarios;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import page_objects.UsedCarsPage;
import utils.DriverSetup;

public class UsedCarsTest {
	UsedCarsPage page;
	WebDriver driver;
	
	public UsedCarsTest() throws Exception {
		driver = DriverSetup.getDriver();
		page = new UsedCarsPage(driver);
	}
	
	@BeforeClass
	public void init() {
		page.launch();
	}
	
	@Test(priority = 1, 
			description = "the listed popular card categories must be 8 in number")
	public void testNumberOfPopularCategories() {
		Assert.assertEquals(page.getPopularModels().size(), 8);
	}
}
