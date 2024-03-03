package page_objects;

import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
	WebDriver driver;
	By emailPhoneInputLocator = By.xpath("//input[@type='email']");
	By nextButtonLocator = By.xpath("//span[text()='Next']");
	By errorMessageLocator = By.xpath("//div[@class = 'o6cuMc Jj6Lae']");
	
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
	
	public boolean singInAccepted() {
		try {
			WebElement errorMessage = driver.findElement(errorMessageLocator);
			return !errorMessage.isDisplayed();
		} catch (NoSuchElementException e){
			return true;
		}
	}
}
