package com.vTiger.crm.hardcodeTest.phase1_organizations;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Tc002_CreateOrganizeandDeleteOrganization 
{
	public static void main(String[] args)
	{
		//launching a browser
		System.setProperty("webdriver.chrome.driver", "./src/main/resources/Driver_Executable/chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		WebDriverWait wait=new WebDriverWait(driver,10);

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

		//selecting  and verifying organization module
		driver.findElement(By.xpath("//a[text()='Organizations']")).click();
		String pageName=driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if(!pageName.trim().equalsIgnoreCase("Organizations"))
			throw new RuntimeException("You're in wrong page");

		//creating organizations
		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();

		Random num=new Random();
		int randomNum=num.nextInt(1000);

		String orgName="Sony"+randomNum;
		String websiteName="https://sony.com"+randomNum;

		//entering the data
		driver.findElement(By.xpath("//input[@name='accountname']")).sendKeys(orgName);
		driver.findElement(By.xpath("//input[@name='website']")).sendKeys(websiteName);
		WebElement saveButton=driver.findElement(By.xpath("//input[@class='crmbutton small save']"));
		saveButton.click();

		//verifying organizations data
		String organizationName=driver.findElement(By.xpath("//span[@id='dtlview_Organization Name']")).getText();
		if(organizationName.equalsIgnoreCase(orgName))
			System.out.println("Organization name is: " + organizationName);
		else
			throw new RuntimeException("Data mismatch");

		String webSiteName=driver.findElement(By.id("dtlview_Website")).getText();
		if(webSiteName.equalsIgnoreCase(websiteName))
			System.out.println("Website Name is: " + webSiteName);
		else
			throw new RuntimeException("Data mismatch");

		//navigate back to organization details page
		driver.findElement(By.xpath("//a[text()='Organizations']")).click();

		//searching the organization
		driver.findElement(By.xpath("//input[@class='txtBox']")).sendKeys(orgName);
		WebElement searchContext=driver.findElement(By.name("search_field"));
		Select select=new Select(searchContext);
		select.selectByValue("accountname");
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
		driver.findElement(By.xpath("//input[@class='txtBox']")).sendKeys(orgName);
		searchContext=driver.findElement(By.name("search_field"));
		select=new Select(searchContext);
		select.selectByValue("accountname");
		driver.findElement(By.name("submit")).click();

		String orgInfo=driver.findElement(By.xpath("//span[@class='genHeaderSmall']")).getText();
		System.out.println(orgInfo);

		//signout from the application
		Actions act=new Actions(driver);
		WebElement administratorElement=driver.findElement(By.xpath("//span[normalize-space(text())='Administrator']/../following-sibling::td[1]/img"));
		act.moveToElement(administratorElement).click(driver.findElement(By.xpath("//a[text()='Sign Out']"))).perform();
	
		driver.quit();
	}

}
