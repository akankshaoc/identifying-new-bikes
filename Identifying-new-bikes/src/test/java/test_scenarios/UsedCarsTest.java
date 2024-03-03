package test_scenarios;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import common.AppTest;
import page_objects.UsedCarsPage;

public class UsedCarsTest extends AppTest{
	UsedCarsPage page;
	
	@Override
	@BeforeClass
	public void initialise() throws Exception {
		homePage.navigateToChennaiUsedCars();
		page = new UsedCarsPage(driver);
	}
	
	@Test(priority = 1, 
			description = "the listed popular card categories must be 9 in number")
	public void testNumberOfPopularCategories() {
		Assert.assertEquals(page.getPopularModels().size(), 9);
	}

	@Override
	@AfterClass
	public void naturalise() throws Exception {
		homePage.launch();		
	}
}
