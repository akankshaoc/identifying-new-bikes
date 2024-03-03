package data_provider;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

public class LoginCredentials {

	@DataProvider(name = "email")
	public String[] getEmail(Method m) throws Exception {
		switch (m.getName()) {
		case "negativeEmailLoginTest":
			return new String[] { "WayfinderChief@oceanexplorers.com", 
					"MrsPotts@enchantedlibrary.com",
					"SebastianConcerts@mermaidmusic.com", 
					"FairyGodmother@magicalboutique.com",
					"DwarvesKitchen@enchantedforest.com" 
					};
		case "positiveEmailLoginTest":
			return new String[] { "akankshasharmaas07@gmail.com"};
		default:
			throw new Exception("cannot provide datat to unlisted method");
		}
	}

	@DataProvider(name = "phone_number")
	public String[] getPhoneNumber(Method m) throws Exception {
		switch(m.getName()) {
		case "negativePhoneNumberLoginTest" : 
			return new String[] { "3333333333", "0000000000", "2222222222"};
		case "positivePhoneNumberLoginTest" : 
			return new String[] { "7037414067"};
		default :
			throw new Exception("cannot provide data to unlisted method");
		}
	}
}
