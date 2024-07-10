package com.vTiger.crm.dataDrivenTest.phase2_contacts;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Tc002_CreatingContactsBySelectingOrganization 
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

		sheet=work.getSheet("Contacts");
		String contact_ID="TC_001";

		for(int i=0; i<=sheet.getLastRowNum(); i++)
		{
			String tcID=df.formatCellValue(sheet.getRow(i).getCell(0));
			if(tcID.equalsIgnoreCase(contact_ID))
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
		String contactsPageText=map.get("ContactPage_Txt");
		String organizationPageText=map.get("OrganizationPage_Txt");
		String contact_InformationPageText=map.get("Contact_InformationPageText");
		String org_Information_PageText=map.get("Org_InformationPageText");
		String invalidPageMsg=map.get("Invalid_Page_Message");
		String invalidDataMsg=map.get("Invalid_Data_Message");
		String firstName=map.get("First_Name")+randomNum;
		String lastName=map.get("Last_Name")+randomNum;
		String mobileNumber="9"+RandomStringUtils.randomNumeric(9,10);
		String org_Name=map.get("Org_Name")+randomNum;
		String website_Name=map.get("Website_Name");

		//launching a browser
		System.setProperty("webdriver.chrome.driver", chromePathValue);
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);

		JavascriptExecutor js=(JavascriptExecutor)driver;
		WebDriverWait wait=new WebDriverWait(driver,timeout);

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

		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[@class='dvHeaderText']"))));

		//navigate into contacts
		WebElement contactElement=driver.findElement(By.xpath("//table[@class='hdrTabBg']//a[text()='Contacts']"));
		js.executeScript("arguments[0].click()",contactElement);		

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

		//switching to organization window
		String parentWindow=driver.getWindowHandle();

		Set<String> set=driver.getWindowHandles();
		for(String word:set)
		{
			driver.switchTo().window(word);
			if(driver.getCurrentUrl().contains("module=Accounts")) 
			{
				String windowName=driver.findElement(By.className("moduleName")).getText();
				if(!windowName.equalsIgnoreCase(organizationPageText))
					throw new RuntimeException(invalidPageMsg);
				break;
			}
		}

		//searching the organization name
		driver.findElement(By.xpath("//input[@id='search_txt']")).sendKeys(org_Name);
		driver.findElement(By.xpath("//input[@name='search']")).click();

		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//div[@id='status']/img"))));
		driver.findElement(By.xpath("//a[contains(text(),'Organization Name')]/ancestor::tr[1]/following-sibling::tr[1]/td[1]/a")).click(); 

		//getting control back to parentwindow
		driver.switchTo().window(parentWindow);
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

		//signout from the application
		Actions act=new Actions(driver);
		WebElement administratorElement=driver.findElement(By.xpath("//span[normalize-space(text())='Administrator']/../following-sibling::td[1]/img"));
		act.moveToElement(administratorElement).click(driver.findElement(By.xpath("//a[text()='Sign Out']"))).perform();

		//write back data
		sheet=work.getSheet("Contacts");
		contact_ID="TC_001";
		String mobileNum_HeaderName="Random_MobileNumber";
		String ranDigit_HeaderName="Random_Digits";

		for(int i=0; i<=sheet.getLastRowNum(); i++)
		{
			String actTcId=df.formatCellValue(sheet.getRow(i).getCell(0));
			if(actTcId.equalsIgnoreCase(contact_ID))
			{
				for(int j=0; j<sheet.getRow(i-1).getLastCellNum(); j++)
				{
					String actHeader=df.formatCellValue(sheet.getRow(i-1).getCell(j));
					if(actHeader.equalsIgnoreCase(mobileNum_HeaderName))
					{
						sheet.getRow(i).createCell(j).setCellValue(mobileNumber);
					}
					else if(actHeader.equalsIgnoreCase(ranDigit_HeaderName)) {
						sheet.getRow(i).createCell(j).setCellValue(randomNum);
					}
				}
				break;
			}
		}

		work.write(new FileOutputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\ExcelFileData\\VTigerdata.xlsx"));
		work.close();
		driver.quit();
	}

}
