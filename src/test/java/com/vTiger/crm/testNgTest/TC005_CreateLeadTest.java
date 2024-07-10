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
import objectRepo.CreateLeadPage;
import objectRepo.HomePage;
import objectRepo.LeadInfoPage;
import objectRepo.LeadPage;
import objectUtlity.TransferObject;

@Listeners(listenerImplementation.ListenImplement.class)
public class TC005_CreateLeadTest extends BaseClassTestNg
{
	@Test(groups="Smoke")
	public void createLead() throws EncryptedDocumentException, IOException
	{
		ExtentTest test=TransferObject.getTest();
		
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
		
		test.log(Status.INFO, "Verifying the data");
		SoftAssert softAssert=new SoftAssert();
		
		String actFname=infoPage.getFnameEle().getText();
		softAssert.assertEquals(actFname.trim(), fname);
//		Reporter.log("FirstName : " + actFname);   			The data will get display in TestNg report, it'll not displayed in local system (console) and not in extentreport. 
		
		String actLname=infoPage.getLnameEle().getText();
		softAssert.assertEquals(actLname.trim(), lname);
//		Reporter.log("LastName : " + actLname);
		
		String actCompanyName=infoPage.getCompanyEle().getText();
		softAssert.assertEquals(actCompanyName.trim(),companyName);
//		Reporter.log("CompanyName : " + actCompanyName);
		
		String actIndustry=infoPage.getIndustryEle().getText();
		softAssert.assertEquals(actIndustry.trim(), industry);
//		Reporter.log("Industry : " + actIndustry);
		
		String actLeadsource=infoPage.getLeadSourceEle().getText();
		softAssert.assertEquals(actLeadsource.trim(), leadSource);
//		Reporter.log("LeadSource : " + actLeadsource);
		
		softAssert.assertAll();
		test.log(Status.PASS, "Data Successfully Verified");
	}
}
