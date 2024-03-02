package data_provider;

import org.testng.annotations.DataProvider;

public class LoginCredentials {
	
	@DataProvider(name = "email")
	public String[] getEmail() {
		return new String[] {
				"WayfinderChief@oceanexplorers.com", 
				"MrsPotts@enchantedlibrary.com", 
				"SebastianConcerts@mermaidmusic.com", 
				"FairyGodmother@magicalboutique.com",
				"DwarvesKitchen@enchantedforest.com"
		};
	}
	
	@DataProvider(name = "phone_number")
	public String[] getPhoneNumber() {
		return new String[] {
				"3333333333", 
				"0000000000", 
				"2222222222",
		};
	}
}
