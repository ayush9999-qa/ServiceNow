package ServiceNow.Gurukula;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Login {
	// Identifying web elements
	@FindBy(id = "username")
	private WebElement username;
	
	@FindBy(id = "password")
	private WebElement password;
	
	@FindBy(css = "button.btn")
	private WebElement authenticate;
	
	@FindBy(partialLinkText = "password")
	private WebElement forgetPassword;
	
	@FindBy(partialLinkText = "Register")
	private WebElement register;
		
	/**
	 * Login to the application
	 * @param : user (username)
	 * @param : pass (password)
	 */
	public void login(String user, String pass) {
		username.sendKeys(user);
		password.sendKeys(pass);
		authenticate.click();
	}
	
	/**
	 * Click on 'Did you forget your password?' link
	 */
	public void forgetPasswordClick() {
		forgetPassword.click();
	}
	
	/**
	 * Click on 'Register a new account'
	 */
	public void registerClick() {
		register.click();
	}
}
