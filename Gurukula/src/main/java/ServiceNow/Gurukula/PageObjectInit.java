package ServiceNow.Gurukula;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PageObjectInit {
	WebDriver driver;
	
	public PageObjectInit(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
}
