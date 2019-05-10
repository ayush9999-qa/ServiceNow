package ServiceNow.Gurukula;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WelcomeLogin extends PageObjectInit {
	
	// Identifying web elements
	@FindBy(css = "div.alert")
	private WebElement welcomeMessage;
		
	@FindBy(xpath = "//*[@class='dropdown-toggle']/span")
	private List<WebElement> dropdown;
	
	@FindBy(css = "ul.dropdown-menu")
	private List<WebElement> dropdownElements;
		
	public WelcomeLogin(WebDriver driver) {
		super(driver);
	}

	/**
	 * This is to select Branch option from the dropdown
	 * @return Branch object
	 */
	public Branch selectBranch() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		dropdown.get(0).click();
		wait.until(ExpectedConditions.visibilityOfAllElements(dropdownElements.get(0).findElements(By.tagName("li"))));
		dropdownElements.get(0).findElements(By.tagName("li")).get(0).click();
		return new Branch(driver);
	}
	
	/**
	 * This is to select Staff option from the dropdown
	 * @return Staff object
	 */
	public Staff selectStaff() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		dropdown.get(0).click();
		wait.until(ExpectedConditions.visibilityOfAllElements(dropdownElements.get(0).findElements(By.tagName("li"))));
		dropdownElements.get(0).findElements(By.tagName("li")).get(1).click();
		return new Staff(driver);
	}
	
	/**
	 * Select 'Settings' option 
	 */
	public void selectSettings() {
		Select entities = new Select(dropdown.get(1));
		entities.selectByVisibleText("Settings");
	}
	
	/**
	 * Select 'Password' option 
	 */
	public void selectPassword() {
		dropdown.get(1).click();
		Select entities = new Select(dropdown.get(1));
		entities.selectByVisibleText("Password");
	}
	
	/**
	 * Select 'Sessions' option 
	 */
	public void selectSessions() {
		Select entities = new Select(dropdown.get(1));
		entities.selectByVisibleText("Sessions");
	}
	
	/**
	 * Select 'Logout' option 
	 */
	public Welcome selectLogout() {
		dropdown.get(1).click();
		dropdownElements.get(1).findElements(By.tagName("li")).get(3).click();
		return new Welcome(driver);
	}
	
	/**
	 * This is to get text displayed after logging in
	 * @return login text
	 */
	public String getLoginText() {
		return welcomeMessage.getText();
	}
}
