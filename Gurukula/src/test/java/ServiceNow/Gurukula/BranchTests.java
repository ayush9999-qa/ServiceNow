package ServiceNow.Gurukula;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class BranchTests extends BaseInit {
	Welcome welcome;
	Login login;
	WelcomeLogin welcomeLogin;
	Branch branch;
	BranchDetails branchDetails;
	String[] branchElements;
	
	@Test(description = "Open branches page", priority = 1)
	public void openBranchPageTest() {
		welcome = new Welcome(driver);
		login = welcome.loginClick();
		welcomeLogin = login.login("admin", "admin");
		branch = welcomeLogin.selectBranch();
		Assert.assertEquals(branch.getTitle(), "Branches");
	}
	
	@Test(description = "Create new branch", priority = 2)
	public void createNewBranchTest() {
		String branchName = "branch";
		String branchCode = "B1";
		branchElements = branch.createBranch(branchName, branchCode);
		
		// 'branchElements' will be null if branch name and code are invalid
		Assert.assertNotNull(branchElements);
		
		// Verify branch elements
		Assert.assertTrue(Integer.parseInt(branchElements[0]) > 0, "Verify if branch with code greater than 0 is created");
		Assert.assertEquals(branchElements[1], branchName, "Verify if branch is added with supplied name");
		Assert.assertEquals(branchElements[2], branchCode, "Verify if branch is added with supplied code");
		System.out.println("Added branch code - " + branchElements[0]);
		System.out.println("Added branch code - " + branchElements[1]);
		System.out.println("Added branch code - " + branchElements[2]);
	}
	
	@Test(description = "Add branch with invalid name", priority = 3)
	public void invalidBranchNameTest() {
		String branchName = "abd@67";
		String error = "";
		String expected = "";
		if (branchName.length() < 5) {
			error = branch.getErrorBranchName(branchName);
			expected = "This field is required to be at least 5 characters.\n" + 
					"This field should follow pattern ^[a-zA-Z\\s]*$.";
			Assert.assertEquals(error, expected);
		}
		else {
			error = branch.getErrorBranchName(branchName);
			expected = "This field should follow pattern ^[a-zA-Z\\s]*$.";
			Assert.assertEquals(error, expected);
		}
		System.out.println(error);
	}
	
	@Test(description = "Add branch with invalid name", priority = 4)
	public void invalidBranchCodeTest() {
		String branchCode = "abd@67";
		String error = "";
		String expected = "";
		if (branchCode.length() < 2) {
			error = branch.getErrorBranchName(branchCode);
			expected = "This field is required to be at least 2 characters.\n" + 
					"This field should follow pattern ^[A-Z0-9]*$.";
			
			Assert.assertEquals(error, expected);
		}
		else {
			error = branch.getErrorBranchCode(branchCode);
			expected = "This field should follow pattern ^[A-Z0-9]*$.";
			Assert.assertEquals(error, expected);
		}
		System.out.println(error);
	}
	
	@Test(description = "View branch", priority = 5)
	public void viewBranchTest() {
		branchDetails = branch.viewBranch();		
		Assert.assertEquals("Branch " + branchElements[0], branchDetails.getTitle());
		
		String[] addedBranch = branchDetails.getBranchDetails();
		Assert.assertEquals(addedBranch[0], branchElements[1]);
		Assert.assertEquals(addedBranch[1], branchElements[2]);		
	}
	
	@Test(description = "Edit branch", priority = 6)
	public void editBranch() {
		String newBranch = "BranchNew";
		String newCode = "BRANCH";
		
		String[] editedValues = branch.editBranch(newBranch, newCode);
		Assert.assertNotNull(editedValues);
		Assert.assertEquals(editedValues[0], newBranch);
		Assert.assertEquals(editedValues[1], newCode);
	}
	
	@Test(description = "Query branch", priority = 7, dependsOnMethods = "createNewBranchTest")
	public void queryBranchByName() {
		// Add another branch to test
		String branchName = "branchNext";
		String branchCode = "B2";
		branchElements = branch.createBranch(branchName, branchCode);
		
		// 'branchElements' will be null branch is not added
		Assert.assertNotNull(branchElements);
		
		// Query branch
		List<String> queryBranches = branch.queryBranchByName(branchName);
		
		// Asserting branch names
		for (String name : queryBranches) {
			Assert.assertTrue(name.contains(branchName));
		}
	}
	
	@Test(description = "Query branch", priority = 7, dependsOnMethods = "createNewBranchTest")
	public void queryBranchByCode() {
		// Add another branch to test
		String branchName = "branchNext";
		String branchCode = "B1";
		branchElements = branch.createBranch(branchName, branchCode);
		
		// 'branchElements' will be null branch is not added
		Assert.assertNotNull(branchElements);
		
		// Query branch
		List<String> queryBranches = branch.queryBranchByCode(branchCode);
		
		// Asserting branch names
		for (String name : queryBranches) {
			Assert.assertTrue(name.contains(branchCode));
		}
	}
	
	@Test(description = "Delete branch", priority = 8, dependsOnMethods = "editBranch")
	public void deleteBranch() {
		// Search for added branch
		branch.queryBranchByName("BranchNew");
		String[] branchIdBeforeAfterDelete = branch.deleteBranch();
		System.out.println("Before delete - " + branchIdBeforeAfterDelete[0]);
		System.out.println("After delete - " + branchIdBeforeAfterDelete[1]);
		Assert.assertFalse(branchIdBeforeAfterDelete[0] == branchIdBeforeAfterDelete[1], "Verify if branch code of last branch before and after delete are not same");
	}
	
}
