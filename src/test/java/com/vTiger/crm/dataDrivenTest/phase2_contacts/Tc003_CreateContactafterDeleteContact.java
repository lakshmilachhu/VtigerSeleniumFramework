package com.vTiger.crm.dataDrivenTest.phase2_contacts;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Tc003_CreateContactafterDeleteContact 
{
	public static void main(String[] args) throws IOException
	{
		//generting random nums
		Random num=new Random();
		int randNum=num.nextInt(100);

		//Setting a property file
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\PropertyFileData\\PropertyFileData.properties");
		Properties prop=new Properties();
		prop.load(fis);
		fis.close();

		//fetching data from property file
		String chromePathValue=System.getProperty("user.dir")+prop.getProperty("chromePath");
		String appUrl=prop.getProperty("url");
		String userName=prop.getProperty("userName");
		String password=prop.getProperty("password");
		long timeout=Long.parseLong(prop.getProperty("timeout"));

		//setting excel file
		fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\ExcelFileData\\VTigerdata.xlsx");
		DataFormatter df=new DataFormatter();
		Workbook work=WorkbookFactory.create(fis);
		fis.close();
		Map<String,String> map=new LinkedHashMap<String, String>();
		Sheet sheet=work.getSheet("Verification");
		String tcId="Verification_001";

		for(int i=0; i<=sheet.getLastRowNum();i++)
		{
			String header=df.formatCellValue(sheet.getRow(i).getCell(0));
			if(header.equalsIgnoreCase(tcId))
			{
				for(int j=0; j<sheet.getRow(i-1).getLastCellNum();j++)
				{
					String key=df.formatCellValue(sheet.getRow(i-1).getCell(j));
					String value=df.formatCellValue(sheet.getRow(i).getCell(j));
					map.put(key, value);
				}
				break;
			}
		}		
		sheet =work.getSheet("Contacts");
		tcId="TC_001";

		for(int i=0; i<=sheet.getLastRowNum(); i++)
		{
			String header=df.formatCellValue(sheet.getRow(i).getCell(0));
			if(header.equalsIgnoreCase(tcId))
			{
				for(int j=0; j<sheet.getRow(i-1).getLastCellNum(); j++)
				{
					String key=df.formatCellValue(sheet.getRow(i-1).getCell(j));
					String value=df.formatCellValue(sheet.getRow(i).getCell(j));
					map.put(key, value);
				}
				break;
			}
		}		
		
		//fetching data from Excel file
		String firstName=map.get("First_Name")+randNum;
		String lastName=map.get("Last_Name")+randNum;
		String mobileNumber="9"+RandomStringUtils.randomNumeric(9, 10);
		String loginBtnVeriTxt=map.get("LoginBtn_Txt");
		String homePageVeriTxt=map.get("HomePage_Txt");
		String contactPageVeriTxt=map.get("ContactPage_Txt");
		String contactCreatePageTXT=map.get("Contact_CreatePageText");
		String searchVeriText=map.get("Search_Box");
		String invalidPageMsg=map.get("Invalid_Page_Message");
		String invalidDataMsg=map.get("Invalid_Data_Message");
		
		//launching a browser
		System.setProperty("webdriver.chrome.driver", chromePathValue);
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);

		WebDriverWait wait=new WebDriverWait(driver,timeout);
		
		//launching a application
		driver.get(appUrl);

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
		Select select=new Select(searchContext);
		select.selectByValue("lastname");
		driver.findElement(By.name("submit")).click();

		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//div[@id='status']/img"))));
		
		//finding particular element to delete it
		driver.findElement(By.xpath("//table[@class='lvt small']//a[text()='del']")).click();

		//to get confirmation for deletion, we'll get popup notiification
		Alert alert=driver.switchTo().alert();
//		String alertNotification=alert.getText();
//		System.out.println(alertNotification);
		alert.accept();

		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//div[@id='status']/img"))));
		
		//verifying the deleted data
		driver.findElement(By.xpath("//input[@class='txtBox']")).sendKeys(lastName);
		searchContext=driver.findElement(By.name("search_field"));
		select=new Select(searchContext);
		select.selectByValue("lastname");
		driver.findElement(By.name("submit")).click();
		
		String contactInfo=driver.findElement(By.xpath("//span[@class='genHeaderSmall']")).getText();
		System.out.println(contactInfo);
		
		//signout from the application
		Actions act=new Actions(driver);
		WebElement administratorElement=driver.findElement(By.xpath("//span[normalize-space(text())='Administrator']/../following-sibling::td[1]/img"));
		act.moveToElement(administratorElement).click(driver.findElement(By.xpath("//a[text()='Sign Out']"))).perform();

		work.close();
		driver.quit();

	}

}
