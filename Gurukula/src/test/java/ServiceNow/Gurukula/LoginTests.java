package ServiceNow.Gurukula;

import org.testng.Assert;
import org.testng.annotations.Test;


public class LoginTests extends BaseInit {
    	
	Login login;
	Welcome welcome;
	WelcomeLogin welcomeLogin;
	
	@Test(description = "Logging with invalid username and password", priority = 1)
	public void loginAdminInvalid() {
		welcome = new Welcome(driver);
		login = welcome.loginClick();
		login.login("admin", "admin1");
		Assert.assertEquals(login.getAuthenticationMessage(), "Authentication failed! Please check your credentials and try again.");
	}
	
	@Test(description = "Logging into the application", priority = 2)
	public void loginAdmin() throws InterruptedException {
		welcomeLogin = login.login("admin", "admin");
		Assert.assertEquals(welcomeLogin.getLoginText(), "You are logged in as user \"admin\".");
		welcomeLogin.selectLogout();
		Thread.sleep(3000);
	}
}
