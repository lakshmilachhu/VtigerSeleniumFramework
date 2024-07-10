package com.vTiger.crm.genericUtilityTest.phase3_organizations;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericUtility.ExcelUtility;
import genericUtility.IConstants;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.SeleniumUtility;

public class Tc002_CreateOrganizeandDeleteOrganization 
{
	public static void main(String[] args) throws IOException
	{
		//Creating instances for utilities
		PropertyFileUtility propertyLib=new PropertyFileUtility();
		ExcelUtility excelLib=new ExcelUtility();
		JavaUtility javaLib=new JavaUtility();	
		SeleniumUtility seleniumLib=new SeleniumUtility();

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
		WebDriver driver=seleniumLib.launchBroswer("chrome");
		seleniumLib.maxBrowser();
		seleniumLib.addImplicitWait(timeout, "Seconds");

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

		//verifying organizations data
		String actOrganizationName=driver.findElement(By.xpath("//span[@id='dtlview_Organization Name']")).getText();
		if(actOrganizationName.equalsIgnoreCase(org_Name))
			System.out.println("Organization name is: " + org_Name);
		else
			throw new RuntimeException(invalidDataMsg);

		String actWebsiteName=driver.findElement(By.id("dtlview_Website")).getText();
		if(actWebsiteName.equalsIgnoreCase(website_Name))
			System.out.println("Website Name is: " + website_Name);
		else
			throw new RuntimeException(invalidDataMsg);

		//navigate back to organization details page
		driver.findElement(By.xpath("//a[text()='Organizations']")).click();

		//searching the organization
		driver.findElement(By.xpath("//input[@class='txtBox']")).sendKeys(org_Name);
		WebElement searchContext=driver.findElement(By.name("search_field"));
		seleniumLib.initSelect(searchContext);
		seleniumLib.selectByValue("accountname");
		driver.findElement(By.name("submit")).click();
		
		WebElement imgEle= driver.findElement(By.xpath("//div[@id='status']/img"));
		seleniumLib.waitForElementInvisibility(imgEle);

		//finding particular element to delete it
		driver.findElement(By.xpath("//table[@class='lvt small']//a[text()='del']")).click();

		//to get confirmation for deletion, we'll get popup notiification
		Alert alert=seleniumLib.switchtoAlert(driver);
		seleniumLib.acceptAlert(alert);

		imgEle=(driver.findElement(By.xpath("//div[@id='status']/img")));
		seleniumLib.waitForElementInvisibility(imgEle);

		//verifying the deleted data
		driver.findElement(By.xpath("//input[@class='txtBox']")).sendKeys(org_Name);
		searchContext=driver.findElement(By.name("search_field"));
		seleniumLib.initSelect(searchContext);
		seleniumLib.selectByValue("accountname");
		driver.findElement(By.name("submit")).click();

		String orgInfo=driver.findElement(By.xpath("//span[@class='genHeaderSmall']")).getText();
		System.out.println(orgInfo);

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
