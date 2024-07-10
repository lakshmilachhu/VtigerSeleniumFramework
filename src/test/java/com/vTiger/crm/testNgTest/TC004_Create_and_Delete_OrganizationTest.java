package com.vTiger.crm.testNgTest;

import java.io.IOException;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import baseClassTestng.BaseClassTestNg;
import genericUtility.IConstants;
import objectRepo.CreateOrgPage;
import objectRepo.HomePage;
import objectRepo.OrgInfoPage;
import objectRepo.OrganizationsPage;
import objectUtlity.TransferObject;

public class TC004_Create_and_Delete_OrganizationTest extends BaseClassTestNg
{
	@Test(groups="Smoke")
	public void createOrg() throws EncryptedDocumentException, IOException
	{
		//creating a Test 
		ExtentTest test=TransferObject.getTest();

		//accessing excel file
		excelLib.initExcelFile(IConstants.excelFilePath);

		//fetching data from Verification, Contacts, Organization page
		Map<String,String> map=excelLib.getAllHeaderAndValues_of_SpecifiedData("Verification", "verification_001");
		Map<String,String> map2=excelLib.getAllHeaderAndValues_of_SpecifiedData("Organization", "TC_002");

		map.putAll(map2);

		String homePageText=map.get("HomePage_Txt");
		String organizationPageText=map.get("OrganizationPage_Txt");
		String org_Information_PageText=map.get("Org_InformationPageText");
		String invalidPageMsg=map.get("Invalid_Page_Message");
		String invalidDataMsg=map.get("Invalid_Data_Message");
		String org_Name=javaLib.getRandOrgName();
		String website_Name=javaLib.getRandWebsiteName();

		HomePage home=new HomePage(driver);

		//verifying home page
		String actHomepageName=home.getHomeTextEle().getText();
		if(!actHomepageName.trim().equalsIgnoreCase(homePageText))
			throw new RuntimeException(invalidPageMsg);

		//navigating to Organization page	
		home.getOrgLink().click();

		OrganizationsPage org=new OrganizationsPage(driver);

		//verifying orginization page
		String actOrganizationpageName=org.getOrgPageTextEle().getText();
		if(!actOrganizationpageName.equalsIgnoreCase(organizationPageText))
			throw new RuntimeException(invalidPageMsg);

		//navigate to create organization
		org.getCreateOrgLink().click();

		CreateOrgPage createOrg=new CreateOrgPage(driver);

		//create organization
		createOrg.createOrg(org_Name, website_Name);

		//Save the data
		createOrg.getSaveBtn().click();

		OrgInfoPage orgInfo=new OrgInfoPage(driver);

		//verifying organization information page
		String actOrgPageName=orgInfo.getOrgInfoPageText().getText();
		if(!actOrgPageName.equalsIgnoreCase(org_Information_PageText))
			throw new RuntimeException(invalidPageMsg);

		//verifying organizations data
		String actOrganizationName=orgInfo.getOrgEle().getText();
		if(actOrganizationName.equalsIgnoreCase(org_Name))
			test.log(Status.INFO,"Organization name is: " + org_Name);
		else
			throw new RuntimeException(invalidDataMsg);

		String actWebsiteName=orgInfo.getWebsiteEle().getText();
		if(actWebsiteName.equalsIgnoreCase(website_Name))
			test.log(Status.INFO,"Website Name is: " + website_Name);
		else
			throw new RuntimeException(invalidDataMsg);		

		//closing workbook and browser
		excelLib.closeWorkBook();
	}

	@Test(groups="Regression")
	public void createOrgandDelete() throws IOException
	{
		//creating a Test 
		ExtentTest test=TransferObject.getTest();
		
		//accessing excel file
		excelLib.initExcelFile(IConstants.excelFilePath);

		//fetching data from Verification, Contacts, Organization page
		Map<String,String> map=excelLib.getAllHeaderAndValues_of_SpecifiedData("Verification", "verification_001");
		Map<String,String> map2=excelLib.getAllHeaderAndValues_of_SpecifiedData("Organization", "TC_002");

		map.putAll(map2);

		String homePageText=map.get("HomePage_Txt");
		String organizationPageText=map.get("OrganizationPage_Txt");
		String org_Information_PageText=map.get("Org_InformationPageText");
		String invalidPageMsg=map.get("Invalid_Page_Message");
		String invalidDataMsg=map.get("Invalid_Data_Message");
		String org_Name=javaLib.getRandOrgName();
		String website_Name=javaLib.getRandWebsiteName();

		//adding explicit wait
		seleniumLib.initExplicitWait(10);

		HomePage home=new HomePage(driver);

		//verifying home page
		String actHomepageName=home.getHomeTextEle().getText();
		if(!actHomepageName.trim().equalsIgnoreCase(homePageText))
			throw new RuntimeException(invalidPageMsg);

		//navigating to Organization page	
		home.getOrgLink().click();

		OrganizationsPage org=new OrganizationsPage(driver);

		//verifying orginization page
		String actOrganizationpageName=org.getOrgPageTextEle().getText();
		if(!actOrganizationpageName.equalsIgnoreCase(organizationPageText))
			throw new RuntimeException(invalidPageMsg);

		//navigate to create organization
		org.getCreateOrgLink().click();

		CreateOrgPage createOrg=new CreateOrgPage(driver);

		//create organization
		createOrg.createOrg(org_Name, website_Name);

		//Save the data
		createOrg.getSaveBtn().click();

		OrgInfoPage orgInfo=new OrgInfoPage(driver);

		//verifying organization information page
		String actOrgPageName=orgInfo.getOrgInfoPageText().getText();
		if(!actOrgPageName.equalsIgnoreCase(org_Information_PageText))
			throw new RuntimeException(invalidPageMsg);

		//verifying organizations data
		String actOrganizationName=orgInfo.getOrgEle().getText();
		if(actOrganizationName.equalsIgnoreCase(org_Name))
			test.log(Status.INFO,"Organization name is: " + org_Name);
		else
			throw new RuntimeException(invalidDataMsg);

		String actWebsiteName=orgInfo.getWebsiteEle().getText();
		if(actWebsiteName.equalsIgnoreCase(website_Name))
			test.log(Status.INFO,"Website Name is: " + website_Name);
		else
			throw new RuntimeException(invalidDataMsg);	

		//navigate back to organization details page
		home.getOrgLink().click();

		//entering required data	
		org.enterSearchtext(org_Name);

		//selecting the category
		WebElement searchContext=org.getCategoryDropdwonEle();
		seleniumLib.initSelect(searchContext);
		seleniumLib.selectByValue("accountname");

		//searching now
		org.getSearchNowEle().click();

		WebElement imgEle=org.getLoadingImg();
		seleniumLib.waitForElementInvisibility(imgEle);

		//finding particular element to delete it
		org.getDeletEle().click();

		//to get confirmation for deletion, we'll get popup notiification
		Alert alert=seleniumLib.switchtoAlert(driver);
		seleniumLib.acceptAlert(alert);

		imgEle=org.getLoadingImg();
		seleniumLib.waitForElementInvisibility(imgEle);

		//verifying the deleted data
		org.enterSearchtext(org_Name);

		//selecting the category
		searchContext=org.getCategoryDropdwonEle();
		seleniumLib.initSelect(searchContext);
		seleniumLib.selectByValue("accountname");

		//searching now
		org.getSearchNowEle().click();

		//deleted verification text
		String orgDeleteInfo=org.getVerificationDeletText().getText();
		System.out.println(orgDeleteInfo);

		//closing workbook
		excelLib.closeWorkBook();
	}

}
