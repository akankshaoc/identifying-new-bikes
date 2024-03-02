package page_objects;

import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
	WebDriver driver;
	By emailPhoneInputLocator = By.xpath("//input[@type='email']");
	By nextButtonLocator = By.xpath("//span[text()='Next']");
	
	LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void fillSignInForm(String email) throws InterruptedException {
		WebElement emailPhoneInput = driver.findElement(emailPhoneInputLocator);
		emailPhoneInput.clear();
		emailPhoneInput.sendKeys(email);
		driver.findElement(nextButtonLocator).click();
		Thread.sleep(2000);
	}
	
	public void captureScreenShot() {
		File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String fileName = Math.random() + "1";
		file.renameTo(new File("./src/test/resources/screenshots/" + fileName + ".png"));
	}
	
	public void switchBackToHomeWindow() {
		String homePageWindowHandle = null;
		String singnInWindow = driver.getWindowHandle();
		for(String windowHandle : driver.getWindowHandles()) {
			if(!windowHandle.equals(singnInWindow)) 
				homePageWindowHandle = windowHandle;
		}
		driver.switchTo().window(homePageWindowHandle);
	}
}
