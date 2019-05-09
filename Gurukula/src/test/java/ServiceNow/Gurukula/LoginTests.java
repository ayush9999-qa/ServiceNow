package ServiceNow.Gurukula;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
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
		WebDriverWait wait = new WebDriverWait(driver, 10);
		welcome = new Welcome(wait);
		login = new Login(wait);		
		
		// Initialize page elements
		driver.get("http://localhost:8080");		
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
		login.login("admin", "admin");
		
	}
}
