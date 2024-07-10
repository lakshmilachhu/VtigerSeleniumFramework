package com.vTiger.crm.testNgTest;

import java.io.IOException;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import baseClassTestng.BaseClassTestNg;
import genericUtility.IConstants;
import objectRepo.ContactInfoPage;
import objectRepo.ContactWindowPage;
import objectRepo.ContactsPage;
import objectRepo.CreateContactPage;
import objectRepo.CreateOpportunityPage;
import objectRepo.HomePage;
import objectRepo.OpportunitiesPage;
import objectRepo.OpportunityInfoPage;
import objectUtlity.TransferObject;

@Listeners(listenerImplementation.ListenImplement.class)
public class TC007_CreateOpportunityTest extends BaseClassTestNg
{
	@Test(groups="Smoke")
	public void createOpportunity() throws EncryptedDocumentException, IOException
	{
		ExtentTest test=TransferObject.getTest();
		
		seleniumLib.initExplicitWait(10);
		
		excelLib.initExcelFile(IConstants.excelFilePath);

		Map<String, String> map1=excelLib.getAllHeaderAndValues_of_SpecifiedData("Opportunities", "TC_111");
		Map<String, String> map2=excelLib.getAllHeaderAndValues_of_SpecifiedData("Verification", "Verification_001");

		String opportunityName=map1.get("Opportunity_Name");
		String relatedField=map1.get("Related_To");
		String expDate=javaLib.getDateBasedOnTAT(30);
		String homePageText=map2.get("HomePage_Txt");
		String opportunityPageText=map2.get("OpportunityPage_Txt");
		String createOpportunityPageText=map2.get("Opportunity_CreatePageText");
		String opportunityInfoPageText=map2.get("Opportunity_InfoPageText");
		String contactPageText=map2.get("ContactPage_Txt");
		String contactCreatePageText=map2.get("Contact_CreatePageText");
	
		String firstName= javaLib.getRandFname();
		String lastName=javaLib.getRandLname();
		String mobileNumber=javaLib.getRandMobNum();

		HomePage home=new HomePage(driver);	
		
		test.log(Status.INFO, "Verifying HomePage");
		String actHomePageName=home.getHomeTextEle().getText();
		Assert.assertEquals(actHomePageName, homePageText);
		test.log(Status.PASS, "HomePage Verified");
		
		test.log(Status.INFO, "Navigating to ContactsPage");
		home.getContactLink().click();
		
		ContactsPage contact=new ContactsPage(driver);
		
		test.log(Status.INFO, "Verifying ContactPage");
		String actContactPage=contact.getContactTextEle().getText();	
		Assert.assertEquals(actContactPage,contactPageText);
		test.log(Status.PASS, "ContactPage Verified");
		
		test.log(Status.INFO, "Navigating to CreateContacts");
		contact.getCreateContactLink().click();

		CreateContactPage createCon=new CreateContactPage(driver);
		
		test.log(Status.INFO, "Verifying CreateContactPage");
		String actCreatePageName=createCon.getCreateConPageText().getText();		
		Assert.assertEquals(actCreatePageName, contactCreatePageText);
		test.log(Status.PASS, "CreateContactPage Verified");

		test.log(Status.INFO, "Filling the Data");
		createCon.createContact(firstName, lastName, mobileNumber);

		createCon.getSaveBtn().click();
		test.log(Status.PASS, "Successfully save the data");
		
		ContactInfoPage contactInfo=new ContactInfoPage(driver);
		
		test.log(Status.INFO, "Navigating to OpportunityPage");
		contactInfo.getToOpportunityPageLink().click();

		OpportunitiesPage opportunity=new OpportunitiesPage(driver);
		
		test.log(Status.INFO, "Verifying OpportunityPage");
		String actOpportunityPageName=opportunity.getOpportunityTextEle().getText();
		Assert.assertEquals(actOpportunityPageName, opportunityPageText);
		test.log(Status.PASS, "OpportunityPage Verified");

		test.log(Status.INFO, "Navigating to CreateOpportunityPage");
		opportunity.getCreateOpportunityEle().click();

		CreateOpportunityPage createOpp=new CreateOpportunityPage(driver);
		
		test.log(Status.INFO, "Verifying CreateOpportunityPage");
		String actCreateOppPageName=createOpp.getCreatingOpportunityText().getText();
		Assert.assertEquals(actCreateOppPageName, createOpportunityPageText);
		test.log(Status.PASS, "CreateOpportunityPage Verified");

		test.log(Status.INFO, "Filling the data");
		createOpp.enterOpportunity(opportunityName);

		WebElement dropdown=createOpp.getRelatedDropdownEle();
		seleniumLib.initSelect(dropdown);
		seleniumLib.selectByText(relatedField);
		
		createOpp.getAddingSourceImgEle().click();
		
		test.log(Status.INFO, "Switching to Contact window, to fecth the Contact details");
		String windowTitle="Contacts";
		seleniumLib.switchToNewWindow_Title(windowTitle);
		
		ContactWindowPage contactWind=new ContactWindowPage(driver);
		
		test.log(Status.INFO, "Verifying ContactsWindowPage");
		String actWindowTitle=contactWind.getContactWindowTitleEle().getText();
		Assert.assertEquals(actWindowTitle, windowTitle);
		test.log(Status.PASS, "ContatcWindowPage Verified");
		
		test.log(Status.INFO, "Accessing the particular data");
		contactWind.searchContact(firstName);
		WebElement searchDropdown=contactWind.getSearchFieldEle();
		seleniumLib.initSelect(searchDropdown);
		seleniumLib.selectByText("First Name");
		contactWind.getSearchBtnElement().click();

		WebElement imgEle=contactWind.getLoadingImgEle();
		seleniumLib.waitForElementInvisibility(imgEle);
				
		contactWind.getSearchedOutputEle().click();
		
		test.log(Status.INFO, "Switching back to OpportunityPage");
		seleniumLib.switchToNewWindow_Url("module=Potentials");
			
		createOpp.enterClosingDate(expDate);
		createOpp.getSaveBtn().click();
		test.log(Status.PASS, "Successfully saved the Data");
		
		OpportunityInfoPage oppInfo=new OpportunityInfoPage(driver);
		
		test.log(Status.INFO, "Verifying OpportunityInfoPage");
		String actInfoPageName=oppInfo.getOpportunityInfoPageText().getText();
		Assert.assertEquals(actInfoPageName, opportunityInfoPageText);
		test.log(Status.PASS, "OpportunityInfoPage Verified");
		
		SoftAssert softAssert=new SoftAssert();
		
		test.log(Status.INFO, "Verifying Data");
		String actOppName=oppInfo.getOpportunityNameText().getText();
		softAssert.assertEquals(actOppName.trim(), opportunityName);
		
		test.log(Status.INFO, "Navigating to through contact-related text link to verify the details of Contacts");
		oppInfo.getTraverseToSelectedContact().click();
		
		String actFname=contactInfo.getFnameEle().getText();
		softAssert.assertEquals(actFname.trim(), firstName);
		
		String actLname=contactInfo.getLnameEle().getText();
		softAssert.assertEquals(actLname.trim(), lastName);
		
		String actMobNum=contactInfo.getMobileEle().getText();
		softAssert.assertEquals(actMobNum.trim(), mobileNumber);
		
		softAssert.assertAll();
		test.log(Status.PASS, "Data Successfully Verified");
		
		test.log(Status.INFO, "Navigating back to OpportunityPage");
		contactInfo.getToOpportunityPageLink().click();
	}

}
