package ServiceNow.Gurukula;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Welcome extends PageObjectInit {
	
	// Identifying web elements
	@FindBy(linkText = "login")
	private WebElement login;
	
	@FindBy(partialLinkText = "Register")
	private WebElement register;
		
	public Welcome(WebDriver driver) {
		super(driver);
	}

	/**
	 * Click on login
	 */
	public Login loginClick() {
		login.click();
		return new Login(driver);
	}
	
	/**
	 * Click on 'Register a new account'
	 */
	public void registerClick() {		
		register.click();
	}
}
