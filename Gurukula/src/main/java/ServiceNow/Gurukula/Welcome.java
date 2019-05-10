package ServiceNow.Gurukula;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Welcome extends PageObjectInit {
	
	// Identifying web elements
	@FindBy(linkText = "login")
	private WebElement login;
	
	@FindBy(partialLinkText = "Register")
	private WebElement register;
	
	@FindBy(css = "div.alert")
	private List<WebElement> loginRegister; 
		
	public Welcome(WebDriver driver) {
		super(driver);
	}

	/**
	 * This is to verify clicking on Login
	 * @return Login object
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
	
	public String getLoginText() {
		return loginRegister.get(0).getText();
	}
}
