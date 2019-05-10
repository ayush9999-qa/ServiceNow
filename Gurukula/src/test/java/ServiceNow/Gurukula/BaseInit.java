package ServiceNow.Gurukula;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

// Class to initialize the driver and start the browser
public class BaseInit {
	public WebDriver driver;
	
	@BeforeClass
	public void setUp() {
		System.setProperty("webdriver.gecko.driver", "resources/geckodriver.exe");
		// Get driver and delete all cookies
		driver = new FirefoxDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://localhost:8080");
	}
	
	@AfterClass
	public void closeDriver() {
		driver.close();
	}
}
