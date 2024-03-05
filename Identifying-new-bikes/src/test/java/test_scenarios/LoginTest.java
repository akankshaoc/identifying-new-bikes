package test_scenarios;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import common.AppTest;
import page_objects.LoginPage;

public class LoginTest extends AppTest {
	LoginPage loginPage;

	@BeforeClass
	public void initialise() throws InterruptedException {
		loginPage = homePage.launchLoginPage();
	}

	@Test(dataProvider = "email", dataProviderClass = data_provider.LoginCredentials.class)
	public void positiveEmailLoginTest(String email) throws InterruptedException {
		loginPage.fillSignInForm(email);
		// wait for ui changes to take place
		Thread.sleep(2000);
		boolean signInAccepted = loginPage.singInAccepted();
		if (signInAccepted) {
			driver.navigate().back();
		}
		Assert.assertTrue(signInAccepted);
	}

	@Test(dataProvider = "email", dataProviderClass = data_provider.LoginCredentials.class)
	public void negativeEmailLoginTest(String email) throws InterruptedException {
		loginPage.fillSignInForm(email);
		// wait for ui changes to take place
		Thread.sleep(2000);
		boolean signInAccepted = loginPage.singInAccepted();
		if (signInAccepted) {
			driver.navigate().back();
		}
		Assert.assertFalse(signInAccepted);
	}

	@Test(dataProvider = "phone_number", dataProviderClass = data_provider.LoginCredentials.class)
	public void positivePhoneNumberLoginTest(String phno) throws InterruptedException {
		loginPage.fillSignInForm(phno);
		// wait for ui changes to take place
		Thread.sleep(2000);
		boolean signInAccepted = loginPage.singInAccepted();
		if (signInAccepted) {
			driver.navigate().back();
		}
		Assert.assertTrue(signInAccepted);
	}

	@Test(dataProvider = "phone_number", dataProviderClass = data_provider.LoginCredentials.class)
	public void negativePhoneNumberLoginTest(String phno) throws InterruptedException {
		loginPage.fillSignInForm(phno);
		// wait for ui changes to take place
		Thread.sleep(2000);
		boolean signInAccepted = loginPage.singInAccepted();
		if (signInAccepted) {
			driver.navigate().back();
		}
		Assert.assertFalse(signInAccepted);
	}

	@AfterClass
	public void naturalise() {
		driver.close();
		driver.switchTo().window(homePage.homePageWindowHandle);
		homePage.closeModal();
	}
}
