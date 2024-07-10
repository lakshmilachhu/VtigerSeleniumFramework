package com.vTiger.crm.genericUtilityTest.phase3_contacts;

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

public class Tc003_CreateContactafterDeleteContact 
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
		String loginBtnVeriTxt=map1.get("LoginBtn_Txt");
		String homePageVeriTxt=map1.get("HomePage_Txt");
		String contactPageVeriTxt=map1.get("ContactPage_Txt");
		String contactCreatePageTXT=map1.get("Contact_CreatePageText");
		String searchVeriText=map1.get("Search_Box");
		String invalidPageMsg=map1.get("Invalid_Page_Message");
		String invalidDataMsg=map1.get("Invalid_Data_Message");

		//launching a browser	
		WebDriver driver=seleniumLib.launchBroswer("chrome");
		seleniumLib.maxBrowser();
		seleniumLib.addImplicitWait(timeout, "Seconds");

		//initializing explicit wait
		seleniumLib.initExplicitWait(timeout);

		//launching a application
		seleniumLib.launchApp(appUrl);

		//verifying login page
		String loginButtonName=driver.findElement(By.id("submitButton")).getAttribute("value");
		if(!loginButtonName.equalsIgnoreCase(loginBtnVeriTxt))
			throw new RuntimeException(invalidPageMsg);

		//login into the application by entering username and password
		driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys(userName);
		driver.findElement(By.xpath("//input[@name='user_password']")).sendKeys(password);
		driver.findElement(By.id("submitButton")).click();

		//verifying home page
		String homepageName=driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if(!homepageName.trim().equalsIgnoreCase(homePageVeriTxt))
			throw new RuntimeException(invalidPageMsg);

		//navigate into contacts
		driver.findElement(By.xpath("//a[text()='Contacts']")).click();

		//verifying contact page
		String currentPage=driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if(!currentPage.equalsIgnoreCase(contactPageVeriTxt))
			throw new RuntimeException(invalidPageMsg);

		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();

		//verifying creating contact page
		String createPageName=driver.findElement(By.className("lvtHeaderText")).getText();
		if(!createPageName.equalsIgnoreCase(contactCreatePageTXT))
			throw new RuntimeException(invalidPageMsg);

		//creating a contact by entering data
		driver.findElement(By.xpath("//input[@name='firstname']")).sendKeys(firstName);
		driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys(lastName);
		driver.findElement(By.xpath("//input[@name='mobile']")).sendKeys(mobileNumber);
		driver.findElement(By.xpath("//input[@class='crmButton small save']")).click();

		//verifying the data
		String actfirstName=driver.findElement(By.id("dtlview_First Name")).getText();
		if(actfirstName.equalsIgnoreCase(firstName))
			System.out.println("FirstName is: " + firstName);
		else
			throw new RuntimeException(invalidDataMsg);

		String actlastName=driver.findElement(By.id("dtlview_Last Name")).getText();
		if(actlastName.equalsIgnoreCase(lastName))
			System.out.println("LastName is: " + lastName);
		else
			throw new RuntimeException(invalidDataMsg);

		String actNumber=driver.findElement(By.id("dtlview_Mobile")).getText();
		if(actNumber.equalsIgnoreCase(mobileNumber))
			System.out.println("ContactNum is: " + mobileNumber);
		else
			throw new RuntimeException(invalidDataMsg);

		//navigate back to contacts, to reach search contact page
		driver.findElement(By.xpath("//a[text()='Contacts']")).click();

		String searchPage=driver.findElement(By.xpath("//div[@id='searchAcc']//span[@class='moduleName']")).getText();
		if(!searchPage.contains(searchVeriText))
			throw new RuntimeException(invalidPageMsg);

		//searching the data
		driver.findElement(By.xpath("//input[@class='txtBox']")).sendKeys(lastName);
		WebElement searchContext=driver.findElement(By.name("search_field"));
		seleniumLib.initSelect(searchContext);
		seleniumLib.selectByValue("lastname");
		driver.findElement(By.name("submit")).click();
		
		WebElement imgEle=driver.findElement(By.xpath("//div[@id='status']/img"));
		seleniumLib.waitForElementInvisibility(imgEle);

		//finding particular element to delete it
		driver.findElement(By.xpath("//table[@class='lvt small']//a[text()='del']")).click();

		//to get confirmation for deletion, we'll get popup notiification
		Alert alert=seleniumLib.switchtoAlert(driver);
		//System.out.println(seleniumLib.getAlertText(alert));
		seleniumLib.acceptAlert(alert);

		imgEle=driver.findElement(By.xpath("//div[@id='status']/img"));
		seleniumLib.waitForElementInvisibility(imgEle);

		//verifying the deleted data
		driver.findElement(By.xpath("//input[@class='txtBox']")).sendKeys(lastName);
		searchContext=driver.findElement(By.name("search_field"));
		seleniumLib.initSelect(searchContext);
		seleniumLib.selectByValue("lastname");
		driver.findElement(By.name("submit")).click();

		String contactInfo=driver.findElement(By.xpath("//span[@class='genHeaderSmall']")).getText();
		System.out.println(contactInfo);

		//Singing out from the application
		seleniumLib.initActions(driver);
		WebElement administratorElement=driver.findElement(By.xpath("//span[normalize-space(text())='Administrator']/../following-sibling::td[1]/img"));
		seleniumLib.move_Element(administratorElement);
		WebElement signout=driver.findElement(By.xpath("//a[text()='Sign Out']"));
		seleniumLib.click_Element(signout);

		//close the workbook and browser
		excelLib.closeWorkBook();
		seleniumLib.closeBrwoser();
	}
}
