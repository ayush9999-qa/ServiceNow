package ServiceNow.Gurukula;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Welcome {
	// Identifying web elements
	@FindBy(linkText = "login")
	private WebElement login;
	
	@FindBy(partialLinkText = "Register")
	private WebElement register;
		
	/**
	 * Click on login
	 */
	public void loginClick() {
		login.click();
	}
	
	/**
	 * Click on 'Register a new account'
	 */
	public void registerClick() {
		register.click();
	}
}
