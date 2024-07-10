package com.vTiger.crm.genericUtilityTest.phase3_contacts;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericUtility.ExcelUtility;
import genericUtility.IConstants;
import genericUtility.JavaScriptUtility;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.SeleniumUtility;

public class Tc002_CreatingContactsBySelectingOrganization 
{
	public static void main(String[] args) throws IOException
	{
		//Creating instances for utilities
		PropertyFileUtility propertyLib=new PropertyFileUtility();
		ExcelUtility excelLib=new ExcelUtility();
		JavaUtility javaLib=new JavaUtility();	
		SeleniumUtility seleniumLib=new SeleniumUtility();
		JavaScriptUtility javascriptLib=new JavaScriptUtility();

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
	// Map<String,String> map2=excelLib.getAllHeaderAndValues_of_SpecifiedData("Organization", "TC_002");
	//	Map<String,String> map3=excelLib.getAllHeaderAndValues_of_SpecifiedData("Contacts", "TC_001");

		String loginPageText=map1.get("LoginBtn_Txt");
		String homePageText=map1.get("HomePage_Txt");
		String contactsPageText=map1.get("ContactPage_Txt");
		String organizationPageText=map1.get("OrganizationPage_Txt");
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
		WebDriver driver=seleniumLib.launchBroswer("chrome");
		seleniumLib.maxBrowser();
		seleniumLib.addImplicitWait(timeout, "Seconds");

		//connecting javascript executor
		javascriptLib.initJavaScriptExecutor(driver);

		//adding explicit wait
		seleniumLib.initExplicitWait(timeout);

		//launching a application
		seleniumLib.launchApp(appUrl);

		//verifying login page
		String loginButtonName=driver.findElement(By.id("submitButton")).getAttribute("value");
		if(!loginButtonName.equalsIgnoreCase(loginPageText))
			throw new RuntimeException(invalidPageMsg);

		//login into the application by entering username and password
		driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys(userName);
		driver.findElement(By.xpath("//input[@name='user_password']")).sendKeys(password);
		driver.findElement(By.id("submitButton")).click();

		//verifying home page
		String homepageName=driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if(!homepageName.trim().equalsIgnoreCase(homePageText))
			throw new RuntimeException(invalidPageMsg);

		//create organization then go to contacts, to avoid dependency
		driver.findElement(By.xpath("//a[text()='Organizations']")).click();
		String organizationpageName=driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if(!organizationpageName.equalsIgnoreCase(organizationPageText))
			throw new RuntimeException(invalidPageMsg);

		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();

		driver.findElement(By.xpath("//input[@name='accountname']")).sendKeys(org_Name);
		driver.findElement(By.xpath("//input[@name='website']")).sendKeys(website_Name);
		driver.findElement(By.xpath("//input[@class='crmbutton small save']")).click();

		//verifying organization page
		String orgPageName=driver.findElement(By.className("dvtSelectedCell")).getText();
		if(!orgPageName.equalsIgnoreCase(org_Information_PageText))
			throw new RuntimeException(invalidPageMsg);

		WebElement ele=driver.findElement(By.xpath("//span[@class='dvHeaderText']"));
		seleniumLib.waitForElementVisibility(ele);
		
		//navigate into contacts
		WebElement contactElement=driver.findElement(By.xpath("//table[@class='hdrTabBg']//a[text()='Contacts']"));
		javascriptLib.toClickElement(contactElement);

		//verifying contact page
		String currentPage=driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if(!currentPage.equalsIgnoreCase(contactsPageText))
			throw new RuntimeException(invalidPageMsg);

		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();

		//creating a contact by entering data
		driver.findElement(By.xpath("//input[@name='firstname']")).sendKeys(firstName);
		driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys(lastName);
		driver.findElement(By.xpath("//input[@name='mobile']")).sendKeys(mobileNumber);

		//selecting organization name
		driver.findElement(By.xpath("//td[normalize-space(.)='Organization Name']/following-sibling::td/img")).click();

		//switching to Organization window
		driver=seleniumLib.switchToNewWindow_Url("module=Accounts");

		//verifying organization window
		String windowName=driver.findElement(By.className("moduleName")).getText();
		if(!windowName.equalsIgnoreCase(organizationPageText))
			throw new RuntimeException(invalidPageMsg);

		//searching the organization name
		driver.findElement(By.xpath("//input[@id='search_txt']")).sendKeys(org_Name);
		driver.findElement(By.xpath("//input[@name='search']")).click();

		WebElement element=driver.findElement(By.xpath("//div[@id='status']/img"));
		seleniumLib.waitForElementInvisibility(element);
		driver.findElement(By.xpath("//a[contains(text(),'Organization Name')]/ancestor::tr[1]/following-sibling::tr[1]/td[1]/a")).click(); 

		//getting control back to parentwindow
		driver=seleniumLib.switchToNewWindow_Url("module=Contacts");

		driver.findElement(By.xpath("//input[@class='crmButton small save']")).click();

		//verifying contact information page
		String contactPageName=driver.findElement(By.className("dvtSelectedCell")).getText();
		if(!contactPageName.equalsIgnoreCase(contact_InformationPageText))
			throw new RuntimeException(invalidPageMsg);

		//verifying the data
		String actOrgName=driver.findElement(By.xpath("//table[@class='small']//td[@id='mouseArea_Organization Name']/a")).getText();
		if(actOrgName.equalsIgnoreCase(org_Name))
			System.out.println("Selected organization is: " + org_Name);
		else
			throw new RuntimeException(invalidDataMsg);

		//Singing out from the application
		seleniumLib.initActions(driver);
		WebElement administratorElement=driver.findElement(By.xpath("//span[normalize-space(text())='Administrator']/../following-sibling::td[1]/img"));
		seleniumLib.move_Element(administratorElement);
		WebElement signout=driver.findElement(By.xpath("//a[text()='Sign Out']"));
		seleniumLib.click_Element(signout);
		
		//closing workbook and browser
		excelLib.closeWorkBook();
		seleniumLib.closeBrwoser();
	}

}
