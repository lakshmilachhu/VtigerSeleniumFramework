package com.vTiger.crm.hardcodeTest.phase1_contacts;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
	public static void main(String[] args)
	{
		//launching a browser
		System.setProperty("webdriver.chrome.driver", ".\\src\\main\\resources\\Driver_Executable\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		JavascriptExecutor js=(JavascriptExecutor)driver;
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		//launching a application
		driver.get("http://localhost:8888");

		//verifying login page
		String loginButtonName=driver.findElement(By.id("submitButton")).getAttribute("value");
		if(!loginButtonName.equalsIgnoreCase("Login"))
			throw new RuntimeException("You're in Wrong page");

		//login into the application by entering username and password
		driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys("admin");
		driver.findElement(By.xpath("//input[@name='user_password']")).sendKeys("admin");
		driver.findElement(By.id("submitButton")).click();

		//verifying home page
		String homepageName=driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if(!homepageName.trim().equalsIgnoreCase("Home"))
			throw new RuntimeException("This is not a Home Page");

		//create organization then go to contacts, to avoid dependency
		driver.findElement(By.xpath("//a[text()='Organizations']")).click();
		String organizationpageName=driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if(!organizationpageName.equalsIgnoreCase("Organizations"))
			throw new RuntimeException("You're in wrong page");

		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();

		Random num=new Random();
		int randomNum=num.nextInt(1000);
		
        String orgName="Sony" + randomNum; //by storing into variable, we can easily access the data multiple times
		driver.findElement(By.xpath("//input[@name='accountname']")).sendKeys(orgName);
		driver.findElement(By.xpath("//input[@name='website']")).sendKeys("sony.com");
		WebElement saveButton=driver.findElement(By.xpath("//input[@class='crmbutton small save']"));
		saveButton.click();

		//verifying organization page
		String orgPageName=driver.findElement(By.className("dvtSelectedCell")).getText();
		if(!orgPageName.equalsIgnoreCase("Organization Information"))
			throw new RuntimeException("This is not a Organization information page");

		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[@class='dvHeaderText']"))));
		
		//navigate into contacts
		WebElement contactElement=driver.findElement(By.xpath("//table[@class='hdrTabBg']//a[text()='Contacts']"));
		js.executeScript("arguments[0].click()",contactElement);		
		
		//verifying contact page
		String currentPage=driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if(!currentPage.equalsIgnoreCase("Contacts"))
			throw new RuntimeException("You're not in Contact page");

		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();

		//random num
		int numData=num.nextInt(100);

		//creating a contact by entering data
		driver.findElement(By.xpath("//input[@name='firstname']")).sendKeys("Lakshmi"+numData);
		driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys("Mohan"+numData);
		driver.findElement(By.xpath("//input[@name='mobile']")).sendKeys("1234567"+ numData);
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
				if(!windowName.equalsIgnoreCase("Organizations"))
					throw new RuntimeException("This is not Organization window");
				break;
			}
		}

		//searching the organization name
		driver.findElement(By.xpath("//input[@id='search_txt']")).sendKeys(orgName);
		driver.findElement(By.xpath("//input[@name='search']")).click();
		
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//div[@id='status']/img"))));
		driver.findElement(By.xpath("//a[contains(text(),'Organization Name')]/ancestor::tr[1]/following-sibling::tr[1]/td[1]/a")).click(); 

		//getting control back to parentwindow
		driver.switchTo().window(parentWindow);
		driver.findElement(By.xpath("//input[@class='crmButton small save']")).click();

		//verifying contact information page
		String contactPageName=driver.findElement(By.className("dvtSelectedCell")).getText();
		if(!contactPageName.equalsIgnoreCase("Contact information"))
			throw new RuntimeException("This is not contact information page");

		//verifying the data
		String actOrgName=driver.findElement(By.xpath("//table[@class='small']//td[@id='mouseArea_Organization Name']/a")).getText();
		if(actOrgName.equalsIgnoreCase(orgName))
			System.out.println("Selected organization is: " + orgName);
		else
			throw new RuntimeException("Wrong organization name");

		//signout from the application
		Actions act=new Actions(driver);
		WebElement administratorElement=driver.findElement(By.xpath("//span[normalize-space(text())='Administrator']/../following-sibling::td[1]/img"));
		act.moveToElement(administratorElement).click(driver.findElement(By.xpath("//a[text()='Sign Out']"))).perform();

		driver.quit();
	}

}
