package ServiceNow.Gurukula;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class StaffTests extends BaseInit {
	Welcome welcome;
	Login login;
	WelcomeLogin welcomeLogin;
	Branch branch;
	Staff staff;
	StaffDetails staffDetails;
	String[] staffElements;
	String branchName = "branch";
	String branchCode = "B1";
	
	@Parameters({"adminUser", "adminPassword"})
	@BeforeClass
	public void createBranchToSelect(String adminUser, String adminPassword) {
		welcome = new Welcome(driver);
		login = welcome.loginClick();
		welcomeLogin = login.login(adminUser, adminPassword);
		branch = welcomeLogin.selectBranch();
		branch.createBranch(branchName, branchCode);
	}
	
	@Test(description = "Verify to open Staffs page", priority = 1)
	public void openStaffPageTest() {		
		staff = welcomeLogin.selectStaff();
		Assert.assertEquals(staff.getTitle(), "Staffs");
	}
	
	@Parameters({"staffName"})
	@Test(description = "Verify to create new staff", priority = 2)
	public void createNewStaffTest(String staffName) {
		staffElements = staff.createStaff(staffName, branchName);
		
		// 'staffElements' will be null if staff name and code are invalid
		Assert.assertNotNull(staffElements);
		
		// Verify staff elements
		Assert.assertTrue(Integer.parseInt(staffElements[0]) > 0, "Verify if staff with code greater than 0 is created");
		Assert.assertEquals(staffElements[1], staffName, "Verify if staff is added with supplied name");
		Assert.assertEquals(staffElements[2], branchName, "Verify if staff is added with selected branch");		
	}
	
	@Parameters({"invalidStaffName"})
	@Test(description = "Verify to add staff with invalid name", priority = 3)
	public void invalidStaffNameTest(String invalidStaffName) {
		String error = "";
		String expected = "";
		if (invalidStaffName.length() > 50) {
			error = staff.getErrorStaffName(invalidStaffName);
			expected = "This field cannot be longer than 50 characters.\n" + 
					"This field should follow pattern ^[a-zA-Z\\s]*$.";
			Assert.assertEquals(error, expected);
		}
		else {
			error = staff.getErrorStaffName(invalidStaffName);
			expected = "This field should follow pattern ^[a-zA-Z\\s]*$.";
			Assert.assertEquals(error, expected);
		}
		System.out.println(error);
	}
	
	@Test(description = "Verify to view added staff", priority = 4)
	public void viewStaffTest() {
		staffDetails = staff.viewBranch();		
		Assert.assertEquals("Staff " + staffElements[0], staffDetails.getTitle());
		
		String[] addedStaff = staffDetails.getStaffDetails();
		Assert.assertEquals(addedStaff[0], staffElements[1]);
		Assert.assertEquals(addedStaff[1], staffElements[2]);		
	}
	
	@Parameters({"newStaff"})
	@Test(description = "Verify to edit added staff", priority = 5)
	public void editStaff(String newStaff) {
		String editedValues = staff.editStaff(newStaff);
		Assert.assertNotNull(editedValues);
		Assert.assertEquals(editedValues, newStaff);
	}
	
	@Parameters({"queryStaffName"})
	@Test(description = "Verify to query staff by staff name", priority = 6, dependsOnMethods = "createNewStaffTest")
	public void queryStaffByName(String queryStaffName) {
		// Add another staff to test
		staffElements = staff.createStaff(queryStaffName, branchName);
		
		// 'staffElements' will be null if staff is not added
		Assert.assertNotNull(staffElements);
		
		// Query staff
		List<String> queryStaffs = staff.queryStaffByName(queryStaffName);
		
		// Asserting staff names
		for (String name : queryStaffs) {
			Assert.assertTrue(name.contains(queryStaffName));
		}
	}
	
	@Parameters({"newStaff"})
	@Test(description = "Verify to delete staff", priority = 7, dependsOnMethods = "editStaff")
	public void deleteStaff(String newStaff) {
		// Search for added staff
		staff.queryStaffByName(newStaff);
		String[] staffIdBeforeAfterDelete = staff.deleteStaff();
		Assert.assertFalse(staffIdBeforeAfterDelete[0] == staffIdBeforeAfterDelete[1], "Verify if staff id of last staff before and after delete are not same");
	}
	
}
