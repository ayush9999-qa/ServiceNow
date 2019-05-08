package ServiceNow.Gurukula;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class LoginTests {
    
	WebDriver driver;
	Login login;
	Welcome welcome;
	
	// Initiate properties and start browser
	@BeforeClass
	public void initiateGurukula() throws InterruptedException {
		System.setProperty("webdriver.gecko.driver", "resources/geckodriver.exe");
		// Get driver and delete all cookies
		WebDriver driver = new FirefoxDriver();
		driver.manage().deleteAllCookies();
		this.driver = driver;
		
		welcome = new Welcome();
		login = new Login();		
		
		// Initialize page elements
		driver.get("http://localhost:8080");
		Thread.sleep(5000);
		PageFactory.initElements(driver, welcome);
		PageFactory.initElements(driver, login);
	}
	
	@AfterClass
	public void closeDriver() {
		driver.close();
	}
	
	@Test
	public void loginAdmin() throws InterruptedException {
		welcome.loginClick();
		Thread.sleep(3000);
		login.login("admin", "admin");
		
	}
}
