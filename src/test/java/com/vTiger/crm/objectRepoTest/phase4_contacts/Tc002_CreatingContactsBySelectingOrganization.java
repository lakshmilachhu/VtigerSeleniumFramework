package com.vTiger.crm.objectRepoTest.phase4_contacts;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericUtility.ExcelUtility;
import genericUtility.IConstants;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.SeleniumUtility;
import objectRepo.ContactInfoPage;
import objectRepo.ContactsPage;
import objectRepo.CreateContactPage;
import objectRepo.CreateOrgPage;
import objectRepo.HomePage;
import objectRepo.LoginPage;
import objectRepo.OrgInfoPage;
import objectRepo.OrgWindowPage;
import objectRepo.OrganizationsPage;

public class Tc002_CreatingContactsBySelectingOrganization 
{
	public static void main(String[] args) throws IOException
	{
		//Creating instances for utilities
		PropertyFileUtility propertyLib=new PropertyFileUtility();
		ExcelUtility excelLib=new ExcelUtility();
		JavaUtility javaLib=new JavaUtility();	
		SeleniumUtility seleniumLib=new SeleniumUtility();
		WebDriver driver;

		//accessing property file
		propertyLib.initPropertyFile(IConstants.propertyFilePath);

		String appUrl=propertyLib.getData("url");
		String userName=propertyLib.getData("userName");
		String password=propertyLib.getData("password");
		long timeout=Long.parseLong(propertyLib.getData("timeout"));
		
		//accessing excel file
		excelLib.initExcelFile(IConstants.excelFilePath);

		//fetching data from Verification, Contacts, Organization page
		Map<String,String> map1=excelLib.getAllHeaderAndValues_of_SpecifiedData("Verification", "verification_001");

		String loginPageText=map1.get("LoginBtn_Txt");
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

		//launching a browser
		driver=seleniumLib.launchBroswer("chrome");
		seleniumLib.maxBrowser();
		seleniumLib.addImplicitWait(timeout, "Seconds");

		//adding explicit wait
		seleniumLib.initExplicitWait(timeout);

		//launching a application
		seleniumLib.launchApp(appUrl);

		LoginPage login=new LoginPage(driver);

		//verifying login page
		String actLoginBtnName=login.getLoginBtnEle().getAttribute("value");
		if(!actLoginBtnName.equalsIgnoreCase(loginPageText))
			throw new RuntimeException(String.format(invalidPageMsg, "Login"));		

		//login into the application by entering username and password	
		login.toLoginApp(userName, password);

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
				System.out.println("FirstName is: " + firstName);
			else
				throw new RuntimeException(invalidDataMsg);

			String actLastName=verification.getLnameEle().getText();
			if(actLastName.equalsIgnoreCase(lastName))
				System.out.println("LastName is: " + lastName);
			else
				throw new RuntimeException(invalidDataMsg);

			String actMobileNum=verification.getMobileEle().getText();
			if(actMobileNum.equalsIgnoreCase(mobileNumber))
				System.out.println("ContactNum is: " + mobileNumber);
			else
				throw new RuntimeException(invalidDataMsg);	

			String actOrgName=verification.getOrgEle().getText();
			if(actOrgName.equalsIgnoreCase(org_Name))
				System.out.println("Selected organization is: " + org_Name);
			else
				throw new RuntimeException(invalidDataMsg);

			//Singing out from the application
			home.logout();

			//closing workbook and browser
			excelLib.closeWorkBook();
			seleniumLib.closeBrwoser();		
	}
}
