package com.vTiger.crm.testNgTest;

import java.io.IOException;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import baseClassTestng.BaseClassTestNg;
import genericUtility.IConstants;
import objectRepo.CreateLeadPage;
import objectRepo.HomePage;
import objectRepo.LeadInfoPage;
import objectRepo.LeadPage;
import objectUtlity.TransferObject;

@Listeners(listenerImplementation.ListenImplement.class)
public class TC006_CreateLead_After_DeleteLeadTest extends BaseClassTestNg
{
	@Test(groups="Regression")
	public void createLead_and_Delete() throws EncryptedDocumentException, IOException
	{
		ExtentTest test=TransferObject.getTest();
		
		seleniumLib.initExplicitWait(10);
		
		excelLib.initExcelFile(IConstants.excelFilePath);

		Map<String,String> map1=excelLib.getAllHeaderAndValues_of_SpecifiedData("Leads", "TC_101");
		Map<String,String> map2=excelLib.getAllHeaderAndValues_of_SpecifiedData("Verification", "Verification_001");

		String fname=javaLib.getRandFname();
		String lname=javaLib.getRandLname();
		String companyName=javaLib.getRandOrgName();
		String industry=map1.get("Industry");
		String leadSource=map1.get("LeadSource");
		String homePageText=map2.get("HomePage_Txt");
		String leadPageText=map2.get("LeadPage_Txt");
		String createLeadPageText=map2.get("Lead_CreatePageText");
		String leadInfoPageText=map2.get("Lead_InfoPageText");

		HomePage home=new HomePage(driver);

		test.log(Status.INFO, "Verifying HomePage");
		String actHomePageName=home.getHomeTextEle().getText();
		Assert.assertEquals(actHomePageName,homePageText);
		test.log(Status.PASS, "HomePage Verified");

		test.log(Status.INFO, "Navigating to Leads");
		home.getLeadsLink().click();

		LeadPage lead=new LeadPage(driver);

		test.log(Status.INFO, "Verifying LeadPage");
		String actLeadPageName=lead.getLeadTextEle().getText();
		Assert.assertEquals(actLeadPageName,leadPageText);
		test.log(Status.PASS, "LeadPage Verified");

		test.log(Status.INFO, "Navigating to creatingLeads");
		lead.getCreateLeadEle().click();

		CreateLeadPage createLead=new CreateLeadPage(driver);

		test.log(Status.INFO, "Verifying LeadCreationPage");
		String actCreateLeadPageName=createLead.getCreateLeadPageText().getText();
		Assert.assertEquals(actCreateLeadPageName,createLeadPageText);
		test.log(Status.PASS, "LeadCreationPage Verified");

		test.log(Status.INFO, "Filling the data with respective fields");
		createLead.createLead(fname, lname, companyName);

		WebElement industryDropdown=createLead.getIndustryDropdownEle();
		seleniumLib.initSelect(industryDropdown);
		seleniumLib.selectByText(industry);

		WebElement leadsourceDropdown=createLead.getLeadsourceDropdownEle();
		seleniumLib.initSelect(leadsourceDropdown);
		seleniumLib.selectByText(leadSource);

		test.log(Status.INFO, "Save the data Successfully");
		createLead.getSaveBtn().click();
		
		LeadInfoPage infoPage=new LeadInfoPage(driver);
		
		test.log(Status.INFO, "Verifying LeadInformationPage");
		String actInfoPageName=infoPage.getLeadInfoPageText().getText();
		Assert.assertEquals(actInfoPageName,leadInfoPageText);
		test.log(Status.PASS, "LeadInformationPage Verified");
		
		test.log(Status.INFO, "Navigating back to leadsPage");
		infoPage.getToLeadPageLink().click();
		
		test.log(Status.INFO, "Verifying Search eniveronment");
		String currentPage=lead.getSearchPageText().getText();
		Assert.assertEquals(currentPage,"Search");
		
		test.log(Status.INFO, "Searching the particular data");
		lead.searchLead(fname);
		WebElement searchDropdown=lead.getcategoryDropdownEle();
		seleniumLib.initSelect(searchDropdown);
		seleniumLib.selectByText("First Name");
		lead.getSearchNowEle().click();
		
		WebElement loadingImg=lead.getLoadingImg();
		seleniumLib.waitForElementInvisibility(loadingImg);
	
		test.log(Status.INFO, "Deleting the searched data");
		lead.getDeletEle().click();
		
		test.log(Status.INFO, "Giving Confirmation to delete");
		Alert alert=seleniumLib.switchtoAlert(driver);
		seleniumLib.acceptAlert(alert);
		test.log(Status.PASS, "Data successfully deleted");
		
		test.log(Status.INFO, "Again searching the deleted element to get confirmation of deletion");
		lead.searchLead(fname);
		searchDropdown=lead.getcategoryDropdownEle();
		seleniumLib.initSelect(searchDropdown);
		seleniumLib.selectByText("First Name");
		lead.getSearchNowEle().click();
		
		loadingImg=lead.getLoadingImg();
		seleniumLib.waitForElementInvisibility(loadingImg);
		
		String verificationText=lead.getVerificationDeletText().getText();
		Assert.assertEquals(verificationText, "No Lead Found !");
		test.log(Status.PASS, "Deleted data is Not Found");
	}

}
