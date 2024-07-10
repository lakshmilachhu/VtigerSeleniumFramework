package com.vTiger.crm.hardcodeTest.phase1_contacts;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Tc001_CreateContacts 
{
	public static void main(String[] args)
	{
		//launching a browser
		System.setProperty("webdriver.chrome.driver", "./src/main/resources/Driver_Executable/chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

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
		
		//generting random nums
		Random num=new Random();
		int numData=num.nextInt(100);
			
		//creating a contact by entering data
		driver.findElement(By.xpath("//input[@name='firstname']")).sendKeys("Lakshmi"+numData);
		driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys("Mohan"+numData);
		driver.findElement(By.xpath("//input[@name='mobile']")).sendKeys("1234567"+ numData);
		driver.findElement(By.xpath("//input[@class='crmButton small save']")).click();
		
		//verifying the data
		String firstName=driver.findElement(By.id("dtlview_First Name")).getText();
		if(firstName.equalsIgnoreCase("lakshmi"+numData))
			System.out.println("FirstName is: " + firstName);
		else
			throw new RuntimeException("Data mismatch");

		String lastName=driver.findElement(By.id("dtlview_Last Name")).getText();
		if(lastName.equalsIgnoreCase("MOHAN"+numData))
			System.out.println("LastName is: " + lastName);
		else
			throw new RuntimeException("Data mismatch");

		String number=driver.findElement(By.id("dtlview_Mobile")).getText();
		if(number.equalsIgnoreCase("1234567"+numData))
			System.out.println("ContactNum is: " + number);
		else
			throw new RuntimeException("Data mismatch");

		//Singing out from the application
		Actions act=new Actions(driver);
		WebElement administratorElement=driver.findElement(By.xpath("//span[normalize-space(text())='Administrator']/../following-sibling::td[1]/img"));
		act.moveToElement(administratorElement).click(driver.findElement(By.xpath("//a[text()='Sign Out']"))).perform();

		driver.quit();
	}

}
