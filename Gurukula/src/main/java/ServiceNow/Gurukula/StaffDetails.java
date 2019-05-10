package ServiceNow.Gurukula;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StaffDetails extends PageObjectInit {

	public StaffDetails(WebDriver driver) {
		super(driver);
	}
	
	// Identifying web elements
	@FindBy(xpath = "//h2")
	private WebElement title;
	
	@FindBy(xpath = "//table/tbody/tr[1]/td[2]/input")
	private WebElement staffName;
	
	@FindBy(xpath = "//table/tbody/tr[2]/td[2]/input")
	private WebElement branchName;
	
	@FindBy(css = "button.btn")
	private WebElement back;
	
	/**
	 * This is to get staff details
	 * @return staff's name and selected branch
	 */
	public String[] getStaffDetails() {
		String[] staffDetails = new String[2];
		staffDetails[0] = staffName.getAttribute("value");
		staffDetails[1] = branchName.getAttribute("value");
		back.click();
		return staffDetails;
	}
	
	/**
	 * This is to get page's title
	 * @return title
	 */
	public String getTitle() {
		return title.getText();
	}
}
