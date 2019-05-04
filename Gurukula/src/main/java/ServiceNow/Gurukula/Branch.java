package ServiceNow.Gurukula;

import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class Branch {
	// Identifying web elements
	@FindBy(css = "button.btn.btn-primary")
	private List<WebElement> createSaveButton;
	
	@FindBy(id = "searchQuery")
	private WebElement searchQuery;
	
	@FindBy(xpath = "//form[@name='searchForm']/button")
	private WebElement searchButton;
	
	@FindBy(xpath = "//table/tbody/tr")
	private List<WebElement> branches;
	
	@FindBy(xpath = "//*[@name='name']")
	private WebElement name;
	
	@FindBy(xpath = "//*[@name='code']")
	private WebElement code;
	
	@FindBy(xpath = "//*[@class='form-group'][2]/div")
	private WebElement branchError;
	
	@FindBy(xpath = "//*[@class='form-group'][3]/div")
	private WebElement codeError;
	
	@FindBy(xpath = "//form[@name='editForm']/div[3]/button[1]")
	private WebElement cancel;
		
	/**
	 * This is to validate name of the branch
	 * @param branchName
	 * @return true/false on basis of name
	 */
	public boolean validateBranchName(String branchName) {		
		if (branchName.length() < 2 || branchName.length() > 10)
			return false;
		if (!Pattern.matches("^[a-zA-Z\\s]*$", branchName))
			return false;
		return true;
	}
	
	/**
	 * This is to validate branch code
	 * @param branchCode
	 * @return true/false on the basis of code
	 */
	public boolean validateBranchCode(String branchCode) {		
		if (branchCode.length() < 5 || branchCode.length() > 20)
			return false;
		if (!Pattern.matches("^[A-Z0-9]*$", branchCode	))
			return false;
		return true;
	}
	
	/**
	 * This is to create branch
	 * @param branchName
	 * @param branchCode
	 * @return branchId
	 */
	public int createBranch(String branchName, String branchCode) {
		if (!validateBranchName(branchName))
			return -1;
		createSaveButton.get(0).click();
		name.sendKeys(branchName);
		code.sendKeys(branchCode);
		createSaveButton.get(1);
		
		// Return last added branch code
		return Integer.parseInt(branches.get(branches.size() - 1).findElement(By.xpath("//*td[1]")).getText());				
	}
	
	public String getErrorBranch(String branchName) {
		name.sendKeys(branchName);
		
		return null;
	}
}
