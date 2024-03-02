package test_scenarios;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import page_objects.UpcomingBikesPage;
import utils.BikeDataReaderWriter;
import utils.DriverSetup;

public class UpcomingBikesTest {

	UpcomingBikesPage page;
	WebDriver driver;
	BikeDataReaderWriter excelIO;

	@BeforeClass
	public void Init() throws Exception {
		this.driver = DriverSetup.getDriver();
		this.page = new UpcomingBikesPage(driver);
		this.excelIO = new BikeDataReaderWriter();
		page.launch();
	}

	public void getDetailsOfUpcomingBikesUnder2Lakhs() throws IOException {
		page.applyManufacturereFilter();
		page.applyPriceFilter();
		List<String[]> results = page.getBikeDetails();
		page.storeDetailsInExcel(results, excelIO);
	}

	@Test(dataProvider = "bikes", 
			dataProviderClass = data_provider.UpcomingBikesData.class, 
			description = "check is any bike with price > 2L was retreived on application of appropriate filters", priority = 1)
	public void priceUnderTwoLakhTest(String name, Double price, LocalDate release) {
		Assert.assertTrue(Double.compare(price, 200000) < 0, name + " bike has price " + price + " > 2L");
	}

	// 2. all the bikes should be upcomming
	@Test(dataProvider = "bikes", 
			dataProviderClass = data_provider.UpcomingBikesData.class, 
			description = "check is any bike with release date of the past was retreived on application of appropriate filters", priority = 1)
	public void releaseInFuture(String name, Double price, LocalDate release) {
		Assert.assertTrue(LocalDate.now().compareTo(release) < 1, name + " has already released");
	}

	@AfterClass
	public void destroy() {
		driver.quit();
	}
}
