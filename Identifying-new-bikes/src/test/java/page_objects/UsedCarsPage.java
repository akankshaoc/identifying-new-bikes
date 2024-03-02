package page_objects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class UsedCarsPage {
	private String url = "https://www.zigwheels.com/used-car/Chennai";
	WebDriver driver;
	By popularModelsLocator = By.xpath("//label[contains(@for,'bycarid')]");
	
	public UsedCarsPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void launch() {
		driver.get(url);
	}
	
	public List<WebElement> getPopularModels() {
		return driver.findElements(popularModelsLocator);
	}
	
	public static void main(String args[]) {
		UsedCarsPage page = new UsedCarsPage(new ChromeDriver());
		page.launch();
		for(WebElement e : page.getPopularModels()) {
			System.out.println(e.getText());
		}
	}
}
