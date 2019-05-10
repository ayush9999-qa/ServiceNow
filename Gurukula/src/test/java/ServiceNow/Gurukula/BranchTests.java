package ServiceNow.Gurukula;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

// This class includes branch tests
public class BranchTests extends BaseInit {
	Welcome welcome;
	Login login;
	WelcomeLogin welcomeLogin;
	Branch branch;
	BranchDetails branchDetails;
	String[] branchElements;
	
	@Parameters({"adminUser", "adminPassword"})
	@Test(description = "Verify to open branch page", priority = 1)
	public void openBranchPageTest(String adminUser, String adminPassword) {
		welcome = new Welcome(driver);
		login = welcome.loginClick();
		welcomeLogin = login.login(adminUser, adminPassword);
		branch = welcomeLogin.selectBranch();
		Assert.assertEquals(branch.getTitle(), "Branches");
	}
	
	@Parameters({"branchName", "branchCode"})
	@Test(description = "Verify to create new branch", priority = 2)
	public void createNewBranchTest(String branchName, String branchCode) {
		branchElements = branch.createBranch(branchName, branchCode);
		
		// 'branchElements' will be null if branch name and code are invalid
		Assert.assertNotNull(branchElements);
		
		// Verify branch elements
		Assert.assertTrue(Integer.parseInt(branchElements[0]) > 0, "Verify if branch with code greater than 0 is created");
		Assert.assertEquals(branchElements[1], branchName, "Verify if branch is added with supplied name");
		Assert.assertEquals(branchElements[2], branchCode, "Verify if branch is added with supplied code");		
	}
	
	@Parameters({"invalidBranchName"})
	@Test(description = "Verify to add branch with invalid name", priority = 3)
	public void invalidBranchNameTest(String invalidBranchName) {
		String error = "";
		String expected = "";
		if (invalidBranchName.length() < 5) {
			error = branch.getErrorBranchName(invalidBranchName);
			expected = "This field is required to be at least 5 characters.\n" + 
					"This field should follow pattern ^[a-zA-Z\\s]*$.";
			Assert.assertEquals(error, expected);
		}
		else {
			error = branch.getErrorBranchName(invalidBranchName);
			expected = "This field should follow pattern ^[a-zA-Z\\s]*$.";
			Assert.assertEquals(error, expected);
		}
	}
	
	@Parameters({"invalidBranchCode"})
	@Test(description = "Verify to add branch with invalid name", priority = 4)
	public void invalidBranchCodeTest(String invalidBranchCode) {
		String error = "";
		String expected = "";
		if (invalidBranchCode.length() < 2) {
			error = branch.getErrorBranchName(invalidBranchCode);
			expected = "This field is required to be at least 2 characters.\n" + 
					"This field should follow pattern ^[A-Z0-9]*$.";
			
			Assert.assertEquals(error, expected);
		}
		else {
			error = branch.getErrorBranchCode(invalidBranchCode);
			expected = "This field should follow pattern ^[A-Z0-9]*$.";
			Assert.assertEquals(error, expected);
		}
	}
	
	@Test(description = "Verify to view added branch", priority = 5)
	public void viewBranchTest() {
		branchDetails = branch.viewBranch();		
		Assert.assertEquals("Branch " + branchElements[0], branchDetails.getTitle());
		
		String[] addedBranch = branchDetails.getBranchDetails();
		Assert.assertEquals(addedBranch[0], branchElements[1]);
		Assert.assertEquals(addedBranch[1], branchElements[2]);		
	}
	
	@Parameters({"newBranch", "newCode"})
	@Test(description = "Verify to edit added branch", priority = 6)
	public void editBranch(String newBranch, String newCode) {	
		String[] editedValues = branch.editBranch(newBranch, newCode);
		Assert.assertNotNull(editedValues);
		Assert.assertEquals(editedValues[0], newBranch);
		Assert.assertEquals(editedValues[1], newCode);
	}
	
	@Parameters({"branchName2", "branchCode2"})
	@Test(description = "Verify to query branches by branch name", priority = 7, dependsOnMethods = "createNewBranchTest")
	public void queryBranchByName(String branchName2, String branchCode2) {
		// Add another branch to test
		branchElements = branch.createBranch(branchName2, branchCode2);
		
		// 'branchElements' will be null branch is not added
		Assert.assertNotNull(branchElements);
		
		// Query branch
		List<String> queryBranches = branch.queryBranchByName(branchName2);
		
		// Asserting branch names
		for (String name : queryBranches) {
			Assert.assertTrue(name.contains(branchName2));
		}
	}
	
	@Parameters({"branchName2", "branchCode2"})
	@Test(description = "Verify to query branch by branch code", priority = 7, dependsOnMethods = "createNewBranchTest")
	public void queryBranchByCode(String branchName2, String branchCode2) {
		// Add another branch to test
		branchElements = branch.createBranch(branchName2, branchCode2);
		
		// 'branchElements' will be null branch is not added
		Assert.assertNotNull(branchElements);
		
		// Query branch
		List<String> queryBranches = branch.queryBranchByCode(branchCode2);
		
		// Asserting branch names
		for (String name : queryBranches) {
			Assert.assertTrue(name.contains(branchCode2));
		}
	}
	
	@Parameters({"newBranch"})
	@Test(description = "Verify to delete branch", priority = 8, dependsOnMethods = "editBranch")
	public void deleteBranch(String newBranch) {
		// Search for added branch
		branch.queryBranchByName(newBranch);
		String[] branchIdBeforeAfterDelete = branch.deleteBranch();
		Assert.assertFalse(branchIdBeforeAfterDelete[0] == branchIdBeforeAfterDelete[1], "Verify if branch code of last branch before and after delete are not same");
	}
	
}
