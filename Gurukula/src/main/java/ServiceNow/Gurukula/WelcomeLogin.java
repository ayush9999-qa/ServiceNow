package ServiceNow.Gurukula;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class WelcomeLogin {
	// Identifying web elements
	@FindBy(css = "div.alert")
	private WebElement welcomeMessage;
	
	@FindBy(xpath = "//*[@class='dropdown-toggle']/span")
	private List<WebElement> dropdown;
		
	/**
	 * Select 'Branch' option 
	 */
	public void selectBranch() {
		Select entities = new Select(dropdown.get(0));
		entities.selectByVisibleText("Branch");
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
	public void selectLogout() {
		Select entities = new Select(dropdown.get(1));
		entities.selectByVisibleText("Log out");
	}
}
