package test_scenarios;

import java.time.LocalDate;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import common.AppTest;
import page_objects.UpcomingBikesPage;
import utils.BikeDataReaderWriter;

public class UpcomingBikesTest extends AppTest{

	UpcomingBikesPage page;

	@BeforeClass
	public void initialise() throws Exception {
		//0. navigating from home to upcoming bikes
		homePage.navigateToUpcommingBikes();
		
		//1. initializing page object
		this.page = new UpcomingBikesPage(driver);
		
		//2. initializing excel reader writer
		BikeDataReaderWriter excelIO = new BikeDataReaderWriter();
		
		//3. applying filters on the page
		page.applyManufacturereFilter();
		page.applyPriceFilter();
		
		//4. storing results to excel sheets
		List<String[]> results = page.getBikeDetails();
		page.storeDetailsInExcel(results, excelIO);
	}

	@Test(dataProvider = "bikes", 
			dataProviderClass = data_provider.UpcomingBikesData.class, 
			description = "check is any bike with price > 2L was retreived on application of appropriate filters", priority = 1)
	public void priceUnderTwoLakhTest(String name, Double price, LocalDate release) {
		Assert.assertTrue(Double.compare(price, 200000) < 0, name + " bike has price " + price + " > 2L");
	}

	@Test(dataProvider = "bikes", 
			dataProviderClass = data_provider.UpcomingBikesData.class, 
			description = "check is any bike with release date of the past was retreived on application of appropriate filters", priority = 1)
	public void releaseInFuture(String name, Double price, LocalDate release) {
		Assert.assertTrue(LocalDate.now().compareTo(release) < 1, name + " has already released");
	}
	
	@AfterClass
	public void naturalise() {
		homePage.launch();
	}
}
