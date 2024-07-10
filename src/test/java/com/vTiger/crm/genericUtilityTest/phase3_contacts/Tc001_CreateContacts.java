package com.vTiger.crm.genericUtilityTest.phase3_contacts;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericUtility.ExcelUtility;
import genericUtility.IConstants;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.SeleniumUtility;

public class Tc001_CreateContacts 
{
	public static void main(String[] args) throws IOException
	{
		//creating instances for utilities
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

		//fetching the data from Verification sheet
		Map<String,String> map1=excelLib.getAllHeaderAndValues_of_SpecifiedData("Verification","Verification_001");

		//fetching data from contact sheet
		Map<String,String> map2=excelLib.getAllHeaderAndValues_of_SpecifiedData("Contacts", "TC_001");

		map1.putAll(map2);

		String firstName= javaLib.getRandFname();
		String lastName=javaLib.getRandLname();
		String mobileNumber=javaLib.getRandMobNum();
				//"9"+javaLib.getRandMobNum();
		String loginBtnVeriTxt=map1.get("LoginBtn_Txt");
		String homePageVeriTxt=map1.get("HomePage_Txt");
		String contactPageVeriTxt=map1.get("ContactPage_Txt");
		String invalidPageMsg=map1.get("Invalid_Page_Message");
		String invalidDataMsg=map1.get("Invalid_Data_Message");

		//launching a browser	
		WebDriver driver=seleniumLib.launchBroswer("chrome");
		seleniumLib.maxBrowser();
		seleniumLib.addImplicitWait(timeout, "Seconds");

		//launching a application
		seleniumLib.launchApp(appUrl);

		//verifying login page
		String loginButtonName=driver.findElement(By.id("submitButton")).getAttribute("value");
		if(!loginButtonName.equalsIgnoreCase(loginBtnVeriTxt))
			throw new RuntimeException(String.format(invalidPageMsg, "Login"));

		//login into the application by entering username and password
		driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys(userName);
		driver.findElement(By.xpath("//input[@name='user_password']")).sendKeys(password);
		driver.findElement(By.id("submitButton")).click();

		//verifying home page
		String homepageName=driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if(!homepageName.trim().equalsIgnoreCase(homePageVeriTxt))
			throw new RuntimeException(String.format(invalidPageMsg, "Home"));

		//navigate into contacts
		driver.findElement(By.xpath("//a[text()='Contacts']")).click();

		//verifying contact page
		String currentPage=driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if(!currentPage.equalsIgnoreCase(contactPageVeriTxt))
			throw new RuntimeException(String.format(invalidPageMsg, "Contact"));

		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();

		//creating a contact by entering data
		driver.findElement(By.xpath("//input[@name='firstname']")).sendKeys(firstName);
		driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys(lastName);
		driver.findElement(By.xpath("//input[@name='mobile']")).sendKeys(mobileNumber);
		driver.findElement(By.xpath("//input[@class='crmButton small save']")).click();

		//verifying the data
		String actFirstName=driver.findElement(By.id("dtlview_First Name")).getText();
		if(actFirstName.equalsIgnoreCase(firstName))
			System.out.println("FirstName is: " + firstName);
		else
			throw new RuntimeException(invalidDataMsg);

		String actLastName=driver.findElement(By.id("dtlview_Last Name")).getText();
		if(actLastName.equalsIgnoreCase(lastName))
			System.out.println("LastName is: " + lastName);
		else
			throw new RuntimeException(invalidDataMsg);

		String actMobileNumber=driver.findElement(By.id("dtlview_Mobile")).getText();
		if(actMobileNumber.equalsIgnoreCase(mobileNumber))
			System.out.println("ContactNum is: " + mobileNumber);
		else
			throw new RuntimeException(invalidDataMsg);

		//Singing out from the application
		seleniumLib.initActions(driver);
		WebElement administratorElement=driver.findElement(By.xpath("//span[normalize-space(text())='Administrator']/../following-sibling::td[1]/img"));
		seleniumLib.move_Element(administratorElement);
		WebElement signout=driver.findElement(By.xpath("//a[text()='Sign Out']"));
		seleniumLib.click_Element(signout);
		
		//closing workbook and driver
		excelLib.closeWorkBook();
		seleniumLib.closeBrwoser();
	}
}
