package ServiceNow.Gurukula;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Welcome {
	WebDriverWait wait;
	
	// Identifying web elements
	@FindBy(linkText = "login")
	private WebElement login;
	
	@FindBy(partialLinkText = "Register")
	private WebElement register;
		
	public Welcome(WebDriverWait wait) {
		this.wait = wait;
	}

	/**
	 * Click on login
	 */
	public void loginClick() {
		wait.until(ExpectedConditions.elementToBeClickable(login));
		login.click();
	}
	
	/**
	 * Click on 'Register a new account'
	 */
	public void registerClick() {
		wait.until(ExpectedConditions.elementToBeClickable(register));
		register.click();
	}
}
