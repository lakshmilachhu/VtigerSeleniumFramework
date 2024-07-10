package com.vTiger.crm.dataDrivenTest.phase2_contacts;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Tc001_CreateContacts 
{
	public static void main(String[] args) throws IOException
	{
		long startTime=System.currentTimeMillis();
		
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
		//		FormulaEvaluator fe = work.getCreationHelper().createFormulaEvaluator();
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
		String invalidPageMsg=map.get("Invalid_Page_Message");
		String invalidDataMsg=map.get("Invalid_Data_Message");

		//launching a browser
		System.setProperty("webdriver.chrome.driver", chromePathValue);
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);

		//launching a application
		driver.get(appUrl);

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
		Actions act=new Actions(driver);
		WebElement administratorElement=driver.findElement(By.xpath("//span[normalize-space(text())='Administrator']/../following-sibling::td[1]/img"));
		act.moveToElement(administratorElement).click(driver.findElement(By.xpath("//a[text()='Sign Out']"))).perform();

		//write back data
		sheet=work.getSheet("Contacts");
		tcId="TC_001";
		String mobileNum_HeaderName="Random_MobileNumber";
		String ranDigit_HeaderName="Random_Digits";
		
		for(int i=0; i<=sheet.getLastRowNum(); i++)
		{
			String actTcId=df.formatCellValue(sheet.getRow(i).getCell(0));
			if(actTcId.equalsIgnoreCase(tcId))
			{
				for(int j=0; j<sheet.getRow(i-1).getLastCellNum(); j++)
				{
					String actHeader=df.formatCellValue(sheet.getRow(i-1).getCell(j));
					if(actHeader.equalsIgnoreCase(mobileNum_HeaderName))
					{
						sheet.getRow(i).createCell(j).setCellValue(mobileNumber);
					}
					else if(actHeader.equalsIgnoreCase(ranDigit_HeaderName)) {
						sheet.getRow(i).createCell(j).setCellValue(randNum);
					}
				}
				break;
			}
		}
		
		work.write(new FileOutputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\ExcelFileData\\VTigerdata.xlsx"));
		work.close();
		driver.quit();
		
		//checking execution time
		long endTime=System.currentTimeMillis();
		long totalExecutionTime=(endTime-startTime)/1000;
		System.out.println("Total Execution Time : "+totalExecutionTime+" sec");
		
	}

}
