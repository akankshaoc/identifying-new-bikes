package page_objects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class HomePage {
	WebDriver driver;
	private String url = "https://www.zigwheels.com/";
	Actions actions;

	// locators
	By newBikesLinkLocator = By.linkText("New Bikes");
	By upcomingBikesLocator = By.xpath("//span[text()='Upcoming Bikes']");
	By usedCarsLocator = By.linkText("Used Cars");
	By chennaiLinkLocator = By.xpath("//div[@class='h-dd-r']//Span[text()='Chennai']");
	By loginButtonLocator = By.id("forum_login_title_lg");
	By loginGoogleButtonLocator = By.xpath("//div[@data-track-label='Popup_Login/Register_with_Google']");
	By closeModalLocator = By.id("report_submit_close_login");

	public HomePage(WebDriver driver) {
		this.driver = driver;
		actions = new Actions(driver);
		this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}

	public void launch() {
		driver.get(url);
		driver.manage().window().maximize();
	}

	public void navigateToUpcommingBikes() {
		actions.moveToElement(driver.findElement(newBikesLinkLocator)).perform();

		driver.findElement(upcomingBikesLocator).click();
	}

	public void navigateToChennaiUsedCars() {
		actions.moveToElement(driver.findElement(usedCarsLocator)).perform();

		driver.findElement(chennaiLinkLocator).click();
	}
	
	public LoginPage launchLoginPage() throws InterruptedException {
		driver.findElement(loginButtonLocator).click();
		Thread.sleep(3000);
		driver.findElement(loginGoogleButtonLocator).click();
		Thread.sleep(3000);
		String homePageWindowHandle = driver.getWindowHandle();

		String singnInWindow = null;
		for(String windowHandle : driver.getWindowHandles()) {
			if(!windowHandle.equals(homePageWindowHandle)) 
				singnInWindow = windowHandle;
		}
		
		driver.switchTo().window(singnInWindow);
		
		return new LoginPage(driver);
	}
	
	public void closeModal() {
		driver.findElement(closeModalLocator).click();
	}
	
	public static void main(String args[]) throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-popup-blocking");
		ChromeDriver driver = new ChromeDriver(options);
		HomePage page = new HomePage(driver);
		page.launch();
		LoginPage lpage = page.launchLoginPage();
		lpage.fillSignInForm("000999888HAHA");
		lpage.captureScreenShot();
	}
}
