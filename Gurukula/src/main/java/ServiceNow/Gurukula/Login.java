package ServiceNow.Gurukula;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Login extends PageObjectInit {
	
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
	
	@FindBy(css = "div.alert-danger")
	private WebElement authenticationError;
		
	public Login(WebDriver driver) {
		super(driver);
	}

	/**
	 * Login to the application
	 * @param : user (username)
	 * @param : pass (password)
	 */
	public WelcomeLogin login(String user, String pass) {
		//wait.until(ExpectedConditions.elementToBeClickable(username));
		username.clear();
		username.sendKeys(user);
		password.clear();
		password.sendKeys(pass);
		authenticate.click();
		return new WelcomeLogin(driver);
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
	
	public String getAuthenticationMessage() {
		return authenticationError.getText();
	}
}
