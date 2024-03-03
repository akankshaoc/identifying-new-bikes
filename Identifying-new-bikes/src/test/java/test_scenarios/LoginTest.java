package test_scenarios;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import common.AppTest;
import page_objects.LoginPage;

public class LoginTest extends AppTest{
	LoginPage loginPage;
	
	@BeforeClass
	public void initialise() throws InterruptedException { 
		loginPage = homePage.launchLoginPage();		
	}
	
	@Test(dataProvider = "email", dataProviderClass = data_provider.LoginCredentials.class)
	public void loginUsingEmail(String email) throws InterruptedException {
		loginPage.fillSignInForm(email);
		loginPage.captureScreenShot();
	}
	
	@Test(dataProvider = "phone_number", dataProviderClass = data_provider.LoginCredentials.class)
	public void loginUsingPhoneNumber(String phno) throws InterruptedException {
		loginPage.fillSignInForm(phno);
		loginPage.captureScreenShot();
	}
	
	@AfterClass
	public void naturalise() {
		driver.close();
		driver.switchTo().window(homePage.homePageWindowHandle);
		homePage.closeModal();
	}
}
