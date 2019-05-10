package ServiceNow.Gurukula;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Branch extends PageObjectInit {
	// Identifying web elements
	@FindBy(xpath = "//h2")
	private WebElement title;
	
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
	
	@FindBy(xpath = "//*[@id='saveBranchModal']/div/div/form/div[2]/div[2]/div")
	private WebElement branchNameError;
	
	@FindBy(xpath = "//*[@id='saveBranchModal']/div/div/form/div[2]/div[3]/div")
	private WebElement branchCodeError;
	
	@FindBy(xpath = "//form[@name='editForm']/div[3]/button[1]")
	private WebElement cancel;
	
	@FindBy(xpath = "//form[@name='editForm']//button")
	private List<WebElement> formButtons;
	
	@FindBy(css = "div.modal-backdrop")
	private WebElement backgroudModal;

	@FindBy(xpath = "//form[@name='deleteForm']//button[2]")
	private WebElement delete;
		
	public Branch(WebDriver driver) {
		super(driver);
	}
	
	public String getTitle() {
		return title.getText();
	}

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
	public String[] createBranch(String branchName, String branchCode) {
		String[] branchElements = new String[3];
		if (!validateBranchName(branchName))
			return null;
		createSaveButton.get(0).click();
		name.sendKeys(branchName);
		code.sendKeys(branchCode);
		formButtons.get(2).click();
		
		// Sleep until overlay modal is completely removed
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
				
		// Return last added branch elements
		branchElements[0] = branches.get(branches.size() - 1).findElement(By.xpath("td[1]")).getText();
		branchElements[1] = branches.get(branches.size() - 1).findElement(By.xpath("td[2]")).getText();
		branchElements[2] = branches.get(branches.size() - 1).findElement(By.xpath("td[3]")).getText();
		return branchElements;
	}
	
	public String getErrorBranchName(String branchName) {
		String nameError = "";		
		createSaveButton.get(0).click();
		name.sendKeys(branchName);
		if (validateBranchName(branchName))
			return "";
		nameError = branchNameError.getText();
		formButtons.get(1).click();
		
		// Sleep until overlay modal is completely removed
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return nameError;
	}
	
	public String getErrorBranchCode(String branchCode) {
		String codeError = "";
		createSaveButton.get(0).click();
		code.sendKeys(branchCode);
		if (validateBranchCode(branchCode))
			return "";
		codeError = branchCodeError.getText();
		formButtons.get(1).click();
		
		// Sleep until overlay modal is completely removed
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return codeError;
	}
	
	public BranchDetails viewBranch() {
		// Check if branch exists
		if (branches.size() <= 0)
			return null;
		
		// Viewing last added branch
		branches.get(branches.size() - 1).findElement(By.xpath("td[4]/button[1]")).click();
		return new BranchDetails(driver);
	}
	
	public String[] editBranch(String newBranchName, String newBranchCode) {
		String[] editedBranchElements = new String[2];
		
		// Check if branch exists
		if (branches.size() <= 0)
			return null;
		
		// Editing last added branch
		branches.get(branches.size() - 1).findElement(By.xpath("td[4]/button[2]")).click();
		
		// Validate new branch name and code
		if (!(validateBranchName(newBranchName)&&validateBranchCode(newBranchCode)))
			return null;
		
		// Edit name and code
		name.clear();
		name.sendKeys(newBranchName);
		code.clear();
		code.sendKeys(newBranchCode);
		
		// Click save
		formButtons.get(2).click();
		
		// Sleep until overlay modal is completely removed
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Return last added branch elements
		editedBranchElements[0] = branches.get(branches.size() - 1).findElement(By.xpath("td[2]")).getText();
		editedBranchElements[1] = branches.get(branches.size() - 1).findElement(By.xpath("td[3]")).getText();
		return editedBranchElements;
	}
	
	public List<String> queryBranchByName(String branchName) {
		List<String> searchElements = new ArrayList<String>();
		searchQuery.clear();
		searchQuery.sendKeys(branchName);
		searchButton.click();
		
		for (WebElement branch : branches) {
			searchElements.add(branch.findElement(By.xpath("td[2]")).getText());
		}
		
		return ((searchElements.size() > 0) ? searchElements : null);
	}
	
	public List<String> queryBranchByCode(String branchCode) {
		List<String> searchElements = new ArrayList<String>();
		searchQuery.clear();
		searchQuery.sendKeys(branchCode);
		searchButton.click();
		
		for (WebElement branch : branches) {
			searchElements.add(branch.findElement(By.xpath("td[3]")).getText());
		}
		
		return ((searchElements.size() > 0) ? searchElements : null);
	}
	
	public String[] deleteBranch() {
		// Storing branch code, before and after deletion
		String elementsDelete[] = new String[2];
		try {
			elementsDelete[0] = branches.get(branches.size() - 1).findElement(By.xpath("td[1]")).getText();
			// Deleting last added branch
			branches.get(branches.size() - 1).findElement(By.xpath("td[4]/button[3]")).click();
			delete.click();
			
			// Sleep until overlay modal is completely removed
			Thread.sleep(500);
			elementsDelete[1] = branches.get(branches.size() - 1).findElement(By.xpath("td[1]")).getText();
		} catch (NoSuchElementException ex) {
			elementsDelete[1] = "";
			ex.printStackTrace();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
				
		return elementsDelete;
	}
}
