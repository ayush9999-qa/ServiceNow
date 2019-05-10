package ServiceNow.Gurukula;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

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
	 * Select 'Branch' option 
	 */
	public Branch selectBranch() {		
		dropdown.get(0).click();
		dropdownElements.get(0).findElements(By.tagName("li")).get(0).click();
		return new Branch(driver);
	}
	
	/**
	 * Select 'Staff' option 
	 */
	public void selectStaff() {
		Select entities = new Select(dropdown.get(0));
		entities.selectByVisibleText("Staff");
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
	
	public String getLoginText() {
		//System.out.println(wait.until(ExpectedConditions.visibilityOf(welcomeMessage)).getText());
		//return wait.until(ExpectedConditions.visibilityOf(welcomeMessage)).getText();
		return welcomeMessage.getText();
	}
}
