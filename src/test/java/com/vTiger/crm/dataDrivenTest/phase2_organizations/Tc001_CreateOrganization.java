package com.vTiger.crm.dataDrivenTest.phase2_organizations;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Tc001_CreateOrganization 
{
	public static void main(String[] args) throws IOException
	{
		//generting random nums
		Random num=new Random();
		int randomNum=num.nextInt(1000);

		//Setting a property file
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\PropertyFileData\\PropertyFileData.properties");
		Properties prop=new Properties();
		prop.load(fis);
		fis.close();

		//fetching data from property file
		String chromePathValue=System.getProperty("user.dir")+prop.getProperty("chromePath");
		long timeout=Long.parseLong(prop.getProperty("timeout"));
		String appUrl=prop.getProperty("url");
		String username=prop.getProperty("userName");
		String password=prop.getProperty("password");

		//setting excel file
		fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\ExcelFileData\\VTigerdata.xlsx");
		DataFormatter df=new DataFormatter();
		Workbook work=WorkbookFactory.create(fis);
		fis.close();
		Map<String,String> map=new LinkedHashMap<String,String>();

		Sheet sheet=work.getSheet("Verification");
		String verification_ID="Verification_001";

		for(int i=0; i<=sheet.getLastRowNum(); i++)
		{
			String tcID=df.formatCellValue(sheet.getRow(i).getCell(0));
			if(tcID.equalsIgnoreCase(verification_ID))
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

		sheet=work.getSheet("Organization");
		String orgTc_ID="TC_002";

		for(int i=0; i<=sheet.getLastRowNum(); i++)
		{
			String tcID=df.formatCellValue(sheet.getRow(i).getCell(0));
			if(tcID.equalsIgnoreCase(orgTc_ID))
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
		String loginPageText=map.get("LoginBtn_Txt");
		String homePageText=map.get("HomePage_Txt");
		String organizationPageText=map.get("OrganizationPage_Txt");
		String org_Information_PageText=map.get("Org_InformationPageText");
		String invalidPageMsg=map.get("Invalid_Page_Message");
		String invalidDataMsg=map.get("Invalid_Data_Message");
		String org_Name=map.get("Org_Name")+randomNum;
		String website_Name=map.get("Website_Name");

		//launching a browser
		System.setProperty("webdriver.chrome.driver", chromePathValue);
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);

		//launching a application
		driver.get(appUrl);

		//verifying login page
		String loginButtonName=driver.findElement(By.id("submitButton")).getAttribute("value");
		if(!loginButtonName.equalsIgnoreCase(loginPageText))
			throw new RuntimeException(invalidPageMsg);

		//login into the application by entering username and password
		driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys(username);
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

		//signout from the application
		Actions act=new Actions(driver);
		WebElement administratorElement=driver.findElement(By.xpath("//span[normalize-space(text())='Administrator']/../following-sibling::td[1]/img"));
		act.moveToElement(administratorElement).click(driver.findElement(By.xpath("//a[text()='Sign Out']"))).perform();

		work.close();
		driver.quit();
	}

}
