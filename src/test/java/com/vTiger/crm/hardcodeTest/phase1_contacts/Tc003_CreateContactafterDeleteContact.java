package com.vTiger.crm.hardcodeTest.phase1_contacts;

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

public class Tc003_CreateContactafterDeleteContact 
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

		//navigate into contacts
		driver.findElement(By.xpath("//a[text()='Contacts']")).click();

		//verifying contact page
		String currentPage=driver.findElement(By.xpath("//a[@class='hdrLink']")).getText();
		if(!currentPage.equalsIgnoreCase("Contacts"))
			throw new RuntimeException("You're not in Contact page");

		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();

		//verifying creating contact page
		String createPageName=driver.findElement(By.className("lvtHeaderText")).getText();
		if(!createPageName.equalsIgnoreCase("Creating New Contact"))
			throw new RuntimeException("This is not a creating contact page");

		//generting random nums
		Random num=new Random();
		int numData=num.nextInt(100);

		String firstName="Lakshmi"+numData;
		String lastName="Mohan"+numData;
		String contactNum="1234567"+numData;
		
		//creating a contact by entering data
		driver.findElement(By.xpath("//input[@name='firstname']")).sendKeys(firstName);
		driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys(lastName);
		driver.findElement(By.xpath("//input[@name='mobile']")).sendKeys(contactNum);
		driver.findElement(By.xpath("//input[@class='crmButton small save']")).click();

		//verifying the data
		String firstNameData=driver.findElement(By.id("dtlview_First Name")).getText();
		if(firstNameData.equalsIgnoreCase(firstName))
			System.out.println("FirstName is: " + firstNameData);
		else
			throw new RuntimeException("Data mismatch");

		String lastNameData=driver.findElement(By.id("dtlview_Last Name")).getText();
		if(lastNameData.equalsIgnoreCase(lastNameData))
			System.out.println("LastName is: " + lastNameData);
		else
			throw new RuntimeException("Data mismatch");

		String numberData=driver.findElement(By.id("dtlview_Mobile")).getText();
		if(numberData.equalsIgnoreCase(contactNum))
			System.out.println("ContactNum is: " + numberData);
		else
			throw new RuntimeException("Data mismatch");

		//navigate back to contacts, to reach search contact page
		driver.findElement(By.xpath("//a[text()='Contacts']")).click();

		String searchPage=driver.findElement(By.xpath("//div[@id='searchAcc']//span[@class='moduleName']")).getText();
		if(!searchPage.contains("Search"))
			throw new RuntimeException("Wrong page");

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
	//	String alertNotification=alert.getText();
	//	System.out.println(alertNotification);
		alert.accept();

		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//div[@id='status']/img"))));
		
		//verifying the deleted data
		driver.findElement(By.xpath("//input[@class='txtBox']")).sendKeys("Mohan11");
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

		driver.quit();

	}

}
