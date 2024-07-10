package com.vTiger.crm.objectRepoTest.phase4_organizations;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import genericUtility.ExcelUtility;
import genericUtility.IConstants;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.SeleniumUtility;
import objectRepo.CreateOrgPage;
import objectRepo.HomePage;
import objectRepo.LoginPage;
import objectRepo.OrgInfoPage;
import objectRepo.OrganizationsPage;

public class Tc001_CreateOrganization 
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
		Map<String,String> map=excelLib.getAllHeaderAndValues_of_SpecifiedData("Verification", "verification_001");
		Map<String,String> map2=excelLib.getAllHeaderAndValues_of_SpecifiedData("Organization", "TC_002");

		map.putAll(map2);

		String loginPageText=map.get("LoginBtn_Txt");
		String homePageText=map.get("HomePage_Txt");
		String organizationPageText=map.get("OrganizationPage_Txt");
		String org_Information_PageText=map.get("Org_InformationPageText");
		String invalidPageMsg=map.get("Invalid_Page_Message");
		String invalidDataMsg=map.get("Invalid_Data_Message");
		String org_Name=javaLib.getRandOrgName();
		String website_Name=javaLib.getRandWebsiteName();
		
		//launching a browser
		driver=seleniumLib.launchBroswer("chrome");
		seleniumLib.maxBrowser();
		seleniumLib.addImplicitWait(timeout, "Seconds");

		//launching a application
		seleniumLib.launchApp(appUrl);

		LoginPage login=new LoginPage(driver);
		
		//verifying login page
		String actLoginButtonName=login.getLoginBtnEle().getAttribute("value");
		if(!actLoginButtonName.equalsIgnoreCase(loginPageText))
			throw new RuntimeException(invalidPageMsg);		

		//login into the application by entering username and password
		login.toLoginApp(userName, password);
		
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
			System.out.println("Organization name is: " + org_Name);
		else
			throw new RuntimeException(invalidDataMsg);
		
		String actWebsiteName=orgInfo.getWebsiteEle().getText();
		if(actWebsiteName.equalsIgnoreCase(website_Name))
			System.out.println("Website Name is: " + website_Name);
		else
			throw new RuntimeException(invalidDataMsg);		

		//Singing out from the application
		home.logout();

		//closing workbook and browser
		excelLib.closeWorkBook();
		seleniumLib.closeBrwoser();
	}

}
