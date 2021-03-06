package ServiceNow.Gurukula;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BranchDetails extends PageObjectInit {

	public BranchDetails(WebDriver driver) {
		super(driver);
	}
	
	// Identifying web elements
	@FindBy(xpath = "//h2")
	private WebElement title;
	
	@FindBy(xpath = "//table/tbody/tr[1]/td[2]/input")
	private WebElement branchName;
	
	@FindBy(xpath = "//table/tbody/tr[2]/td[2]/input")
	private WebElement branchCode;
	
	@FindBy(css = "button.btn")
	private WebElement back;
	
	/**
	 * This is to verify branch details
	 * @return array of branch name and branch code
	 */
	public String[] getBranchDetails() {
		String[] branchDetails = new String[2];
		branchDetails[0] = branchName.getAttribute("value");
		branchDetails[1] = branchCode.getAttribute("value");
		back.click();
		return branchDetails;
	}
	
	/**
	 * This is to verify the title of the page
	 * @return title
	 */
	public String getTitle() {
		return title.getText();
	}
	
}
