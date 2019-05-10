package ServiceNow.Gurukula;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class LoginTests extends BaseInit {
    	
	Login login;
	Welcome welcome;
	WelcomeLogin welcomeLogin;
	
	@Parameters({"invalidAdminUser", "adminPassword"})
	@Test(description = "Verify to logn with invalid username and password", priority = 1)
	public void loginAdminInvalid(String invalidAdminUser, String adminPassword) throws InterruptedException {
		welcome = new Welcome(driver);
		login = welcome.loginClick();
		login.login(invalidAdminUser, adminPassword);
		Thread.sleep(500);
		Assert.assertEquals(login.getAuthenticationMessage(), "Authentication failed! Please check your credentials and try again.");
	}
	
	@Parameters({"adminUser", "adminPassword"})
	@Test(description = "Verify to login with valid username and password", priority = 2)
	public void loginAdmin(String adminUser, String adminPassword) {
		welcomeLogin = login.login(adminUser, adminPassword);
		Assert.assertEquals(welcomeLogin.getLoginText(), "You are logged in as user \"admin\".");
	}
	
	@Test(description = "Verify to log out from the application", priority = 3)
	public void logout() {
		welcome = welcomeLogin.selectLogout();
		Assert.assertEquals(welcome.getLoginText(), "Click here to login");
	}
}
