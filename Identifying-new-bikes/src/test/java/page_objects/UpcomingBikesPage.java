package page_objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import utils.BikeDataReaderWriter;
import utils.CustomPriceFormatter;

public class UpcomingBikesPage {

	WebDriver driver;
	private String url = "https://www.zigwheels.com/upcoming-bikes";
	Actions actions;
	JavascriptExecutor js;

	By manufacturesLocator = By.id("makeId");
	By under2LakhLinkLocator = By.xpath("//div[@class='zw-con pb-0 mb-20 stickyRhs']//li[4]/a");
	By bikeLocator = By.cssSelector("#modelList>li:not(.clr)");
	By viewMoreLocator = By.xpath("//ul[@id='modelList']/li/span");
	
	/**
	 * initializing driver, js executor and actions objects
	 * @param driver
	 */
	public UpcomingBikesPage(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
		actions = new Actions(driver);
	}
	
	/**
	 * launching web page on a new maximised window
	 */
	public void launch() {
		driver.get(url);
		driver.manage().window().maximize();
	}
	
	/**
	 * applying manufacturer filter on Honda
	 */
	public void applyManufacturereFilter() {
		Select manufacturerDropdown = new Select(driver.findElement(manufacturesLocator));
		manufacturerDropdown.selectByVisibleText("Honda");
	}
	
	/**
	 * apply filter on price under 2 lakhs
	 */
	public void applyPriceFilter() {
		driver.findElement(under2LakhLinkLocator).sendKeys(Keys.ENTER);
	}
	
	/**
	 * returns all the bikes retrieved on the page
	 * @return list of li web elements listing all the bikes
	 */
	public List<WebElement> getAllBikes() {
		//to click view more
		js.executeScript("document.querySelector(`#modelList>li>span`).click();");
		return driver.findElements(bikeLocator);
	}
	
	/**
	 * get a list of all the bike's details
	 * @return [[bike name, bike price, expected launch date], ...]
	 */
	public List<String[]> getBikeDetails() {
		//1. initializing resulting list
		List<String[]> res = new ArrayList<>();
		
		//2. storing all bike details in res
		for(WebElement bikeListItem : this.getAllBikes()) {
			//2.1. creating auxiliary storing array
			String aux[] = new String[3];
			aux[0] = bikeListItem.findElement(By.cssSelector("a>strong")).getText();
			aux[1] = bikeListItem.findElement(By.xpath("//div[contains(@title, 'Price')]")).getText();
			aux[2] = bikeListItem.findElement(By.xpath("//div[contains(text(), 'Launch Date')]")).getText();
			
			//2.2. cleaning date String
			aux[2] = aux[2].split(":")[1].trim();
			
			//2.3 cleaning price String
			aux[1] = Double.toString(CustomPriceFormatter.format(aux[1]));
			
			//2.4. adding auxiliary string to res
			res.add(aux);
		}
		
		//3. return result
		return res;
	}
	
	public void storeDetailsInExcel(List<String[]> details, BikeDataReaderWriter excelIO) throws IOException {
		excelIO.writeToNewBikes(new String[] {"name", "price", "dates"}, details);
	}

	public static void main(String args[]) throws InterruptedException {
		UpcomingBikesPage page = new UpcomingBikesPage(new ChromeDriver());
		page.launch();
		page.applyManufacturereFilter();
		page.applyPriceFilter();		
		for(String[] details: page.getBikeDetails()) {
			System.out.print(details[0] + " ");
			System.out.print(details[1] + " ");
			System.out.print(details[2] + " ");
			System.out.println();
		}
	}

}
