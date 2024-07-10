package com.vTiger.crm.testNgTest;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import baseClassTestng.BaseClassTestNg;
import genericUtility.IConstants;
import objectRepo.ContactInfoPage;
import objectRepo.ContactsPage;
import objectRepo.CreateContactPage;
import objectRepo.CreateOrgPage;
import objectRepo.HomePage;
import objectRepo.OrgInfoPage;
import objectRepo.OrgWindowPage;
import objectRepo.OrganizationsPage;
import objectUtlity.TransferObject;

public class TC002_CreatingContactsBySelectingOrganizationTest extends BaseClassTestNg
{
	@Test(groups="Regression")
	public void createContactWithOrg() throws IOException
	{
		//creating a Test 
		ExtentTest test=TransferObject.getTest();
		
		//accessing excel file
		excelLib.initExcelFile(IConstants.excelFilePath);

		//fetching data from Verification, Contacts, Organization page
		Map<String,String> map1=excelLib.getAllHeaderAndValues_of_SpecifiedData("Verification", "verification_001");

		String homePageText=map1.get("HomePage_Txt");
		String contactsPageText=map1.get("ContactPage_Txt");
		String organizationPageText=map1.get("OrganizationPage_Txt");
		String contactCreatePageTXT=map1.get("Contact_CreatePageText");
		String contact_InformationPageText=map1.get("Contact_InformationPageText");
		String org_Information_PageText=map1.get("Org_InformationPageText");
		String invalidPageMsg=map1.get("Invalid_Page_Message");
		String invalidDataMsg=map1.get("Invalid_Data_Message");
		String firstName= javaLib.getRandFname();
		String lastName=javaLib.getRandLname();
		String mobileNumber=javaLib.getRandMobNum();
		String org_Name=javaLib.getRandOrgName();
		String website_Name=javaLib.getRandWebsiteName();

		//adding explicit wait
		seleniumLib.initExplicitWait(10);

		HomePage home=new HomePage(driver);

		//verifying home page
		String actHomepageName=home.getHomeTextEle().getText();
		if(!actHomepageName.trim().equalsIgnoreCase(homePageText))
			throw new RuntimeException(String.format(invalidPageMsg, "Home"));

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

		//Saving the data
		createOrg.getSaveBtn().click();

		OrgInfoPage orgInfo=new OrgInfoPage(driver);

		//verifying organization information page
		String actOrgPageName=orgInfo.getOrgInfoPageText().getText();
		if(!actOrgPageName.equalsIgnoreCase(org_Information_PageText))
			throw new RuntimeException(invalidPageMsg);

		WebElement visibleText=orgInfo.getVisibleText();
		seleniumLib.waitForElementVisibility(visibleText);

		//navigate to contatcs page from organizationInformationPage
		orgInfo.gettoContactPageLink().click();

		ContactsPage contact=new ContactsPage(driver);

		//verifying contact page
		String actContactPage=contact.getContactTextEle().getText();	
		if(!actContactPage.equalsIgnoreCase(contactsPageText))
			throw new RuntimeException(String.format(invalidPageMsg, "Contact"));

		//navigate into create contacts
		contact.getCreateContactLink().click();

		CreateContactPage createCon=new CreateContactPage(driver);

		//verifying creating contact page
		String createPageName=createCon.getCreateConPageText().getText();		
		if(!createPageName.equalsIgnoreCase(contactCreatePageTXT))
			throw new RuntimeException(invalidPageMsg);


		//creating a contact by entering data
		createCon.createContact(firstName, lastName, mobileNumber);

		//click on orgnizationCreate element to select org
		createCon.getCreateOrgEle().click();

		//switching to Organization window
		driver=seleniumLib.switchToNewWindow_Url("module=Accounts");

		OrgWindowPage orgWind=new OrgWindowPage(driver);

		//verifying organization window
		String actOrgWindowName=orgWind.getOrgWindowTitleEle().getText();
		if(!actOrgWindowName.equalsIgnoreCase(organizationPageText))
			throw new RuntimeException(invalidPageMsg);

		//searching the organization name
		orgWind.searchOrg(org_Name);

		//waiting for invisible of loading img element
		WebElement loadImgEle=orgWind.getLoadingImgEle();
		seleniumLib.waitForElementInvisibility(loadImgEle);

		//selecting the searched orgName
		orgWind.getSearchedOutputEle().click();

		//getting control back to parentwindow i,e Contacts page
		driver=seleniumLib.switchToNewWindow_Url("module=Contacts");

		//saving the data
		createCon.getSaveBtn().click();

		ContactInfoPage verification=new ContactInfoPage(driver);

		//verifying contact information page
		String actContactInfoPageName=verification.getContactInfoPageText().getText();
		if(!actContactInfoPageName.equalsIgnoreCase(contact_InformationPageText))
			throw new RuntimeException(invalidPageMsg);

		//verifying the data
		String actFirstName=verification.getFnameEle().getText();
		if(actFirstName.equalsIgnoreCase(firstName))
			test.log(Status.INFO,"FirstName is: " + firstName);
		else
			throw new RuntimeException(invalidDataMsg);

		String actLastName=verification.getLnameEle().getText();
		if(actLastName.equalsIgnoreCase(lastName))
			test.log(Status.INFO,"LastName is: " + lastName);
		else
			throw new RuntimeException(invalidDataMsg);

		String actMobileNum=verification.getMobileEle().getText();
		if(actMobileNum.equalsIgnoreCase(mobileNumber))
			test.log(Status.INFO,"ContactNum is: " + mobileNumber);
		else
			throw new RuntimeException(invalidDataMsg);	

		String actOrgName=verification.getOrgEle().getText();
		if(actOrgName.equalsIgnoreCase(org_Name))
			test.log(Status.INFO,"Selected organization is: " + org_Name);
		else
			throw new RuntimeException(invalidDataMsg);

		//closing workbook
		excelLib.closeWorkBook();
	}
}
