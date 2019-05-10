package ServiceNow.Gurukula;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

// This is the parent class to all the Page classes
// This will initialize page elements whenever page class is initialized
public class PageObjectInit {
	WebDriver driver;
	
	public PageObjectInit(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
}
