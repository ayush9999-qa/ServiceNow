package ServiceNow.Gurukula;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class Staff extends PageObjectInit {

	public Staff(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath = "//h2")
	private WebElement title;
	
	@FindBy(css = "button.btn.btn-primary")
	private List<WebElement> createButton;
	
	@FindBy(id = "searchQuery")
	private WebElement searchQuery;
	
	@FindBy(xpath = "//form[@name='searchForm']/button")
	private WebElement searchButton;
	
	@FindBy(xpath = "//table/tbody/tr")
	private List<WebElement> staffs;
	
	@FindBy(xpath = "//*[@name='name']")
	private WebElement name;
	
	@FindBy(xpath = "//*[@name='related_branch']")
	private WebElement branchDropdown;
	
	@FindBy(xpath = "//*[@id='saveStaffModal']/div/div/form/div[2]/div[2]/div")
	private WebElement staffNameError;
	
	@FindBy(xpath = "//form[@name='editForm']//button")
	private List<WebElement> formButtons;
	
	@FindBy(xpath = "//form[@name='deleteForm']//button[2]")
	private WebElement delete;
	
	/**
	 * This is to validate title of the page
	 * @return title
	 */
	public String getTitle() {
		return title.getText();
	}
	
	/**
	 * This is to validate staff name
	 * @param staffName
	 * @return true/false
	 */
	public boolean validateStaffName(String staffName) {		
		if (staffName.length() <= 0 || staffName.length() > 50)
			return false;
		if (!Pattern.matches("^[a-zA-Z\\s]*$", staffName))
			return false;
		return true;
	}
	
	/**
	 * This is to verify creation of staff
	 * @param staffName
	 * @param branchName
	 * @return staffId, staff name and staff's branch
	 */
	public String[] createStaff(String staffName, String branchName) {
		String[] staffElements = new String[3];
		
		// Check if staff name is valid
		if (!validateStaffName(branchName))
			return null;
		
		// Create staff
		createButton.get(0).click();
		name.sendKeys(staffName);
		Select dropdown = new Select(branchDropdown);
		dropdown.selectByVisibleText(branchName);
		formButtons.get(2).click();
		
		// Sleep until overlay modal is completely removed
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
				
		// Return last added branch elements
		staffElements[0] = staffs.get(staffs.size() - 1).findElement(By.xpath("td[1]")).getText();
		staffElements[1] = staffs.get(staffs.size() - 1).findElement(By.xpath("td[2]")).getText();
		staffElements[2] = staffs.get(staffs.size() - 1).findElement(By.xpath("td[3]")).getText();
		return staffElements;
	}

	/**
	 * This is to validate error after entering incorrect staff name
	 * @param staffName
	 * @return error message
	 */
	public String getErrorStaffName(String staffName) {
		String nameError = "";		
		createButton.get(0).click();
		name.sendKeys(staffName);
		if (validateStaffName(staffName))
			return "";
		nameError = staffNameError.getText();
		formButtons.get(1).click();
		
		// Sleep until overlay modal is completely removed
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return nameError;
	}

	/**
	 * This is to click on 'View' staff
	 * @return StaffDetails object
	 */
	public StaffDetails viewBranch() {
		// Check if staff exists
		if (staffs.size() <= 0)
			return null;
		
		// Viewing last added staff
		staffs.get(staffs.size() - 1).findElement(By.xpath("td[4]/button[1]")).click();
		return new StaffDetails(driver);
	}

	/**
	 * This is to verify editing of staff name
	 * @param newStaffName
	 * @return edited staff name
	 */
	public String editStaff(String newStaffName) {
		String editedStaffName;
		
		// Check if staff exists
		if (staffs.size() <= 0)
			return null;
		
		// Editing last added staff
		staffs.get(staffs.size() - 1).findElement(By.xpath("td[4]/button[2]")).click();
		
		// Validate new staff name
		if (!(validateStaffName(newStaffName)))
			return null;
		
		// Edit name
		name.clear();
		name.sendKeys(newStaffName);
		
		// Click save
		formButtons.get(2).click();
		
		// Sleep until overlay modal is completely removed
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Return last edited staff name
		editedStaffName = staffs.get(staffs.size() - 1).findElement(By.xpath("td[2]")).getText();
		return editedStaffName;
	}

	/**
	 * This is to verify querying the branch by branch name
	 * @param staffName
	 * @return list of returned staffs
	 */
	public List<String> queryStaffByName(String staffName) {
		List<String> searchElements = new ArrayList<String>();
		searchQuery.clear();
		searchQuery.sendKeys(staffName);
		searchButton.click();
		
		for (WebElement staff : staffs) {
			searchElements.add(staff.findElement(By.xpath("td[2]")).getText());
		}
		
		return ((searchElements.size() > 0) ? searchElements : null);
	}
	
	/**
	 * This is to verify deletion of staff
	 * @return staff id before and after deleting
	 */
	public String[] deleteStaff() {
		// Storing staff Id, before and after deletion
		String elementsDelete[] = new String[2];
		try {
			elementsDelete[0] = staffs.get(staffs.size() - 1).findElement(By.xpath("td[1]")).getText();
			
			// Deleting last added staff
			staffs.get(staffs.size() - 1).findElement(By.xpath("td[4]/button[3]")).click();
			delete.click();
			
			// Sleep until overlay modal is completely removed
			Thread.sleep(500);
			elementsDelete[1] = staffs.get(staffs.size() - 1).findElement(By.xpath("td[1]")).getText();
		} catch (NoSuchElementException ex) {
			elementsDelete[1] = "";
			ex.printStackTrace();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
				
		return elementsDelete;
	}
}
