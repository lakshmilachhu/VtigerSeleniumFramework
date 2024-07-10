package baseClassTestng;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import genericUtility.ExcelUtility;
import genericUtility.IConstants;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.SeleniumUtility;
import objectRepo.HomePage;
import objectRepo.LoginPage;
import objectUtlity.TransferObject;

public class BaseClassTestNg 
{
	protected WebDriver driver;
//line added
	//master
	//child
	//creating instances for utilities
	protected PropertyFileUtility propertyLib=new PropertyFileUtility();
	protected ExcelUtility excelLib=new ExcelUtility();
	protected JavaUtility javaLib=new JavaUtility();
	protected SeleniumUtility seleniumLib=new SeleniumUtility();

	@BeforeSuite(alwaysRun = true)
	public void BeforeSuite()
	{
		System.out.println("============= Connecting with DB ==============");
	}

	@Parameters("BROWSER")
	@BeforeClass(alwaysRun = true)
	public void BeforeClass(@Optional String browser) throws IOException
	{
		System.out.println("=============Launching the Browser==============");

		//accessing property file
		propertyLib.initPropertyFile(IConstants.propertyFilePath);

		browser= (browser==null) ? (browser=propertyLib.getData("browser")) : browser;
		
		//launching a browser
		driver = seleniumLib.launchBroswer(browser);
		TransferObject.setDriver(driver); 
		
		seleniumLib.maxBrowser();
		long timeout=Long.parseLong(propertyLib.getData("timeout"));
		seleniumLib.addImplicitWait(timeout, "Seconds");
	}

	@BeforeMethod(alwaysRun = true)
	public void BeforeMethod() throws IOException
	{
		String appUrl=propertyLib.getData("url");
		String userName=propertyLib.getData("userName");
		String password=propertyLib.getData("password");

		System.out.println("============Launching the URL============");
		seleniumLib.launchApp(appUrl);

		System.out.println("============Login into the Application============");
		LoginPage login=new LoginPage(driver);
		login.toLoginApp(userName, password);
	}

	@AfterMethod(alwaysRun = true)
	public void AfterMethod()
	{
		System.out.println("============Logout from the Application============");
		HomePage home=new HomePage(driver);
		home.logout();
	}

	@AfterClass(alwaysRun = true)
	public void AfterClass()
	{
		System.out.println("=============Closing the Browser==============");
		seleniumLib.closeBrwoser();
	}

	@AfterSuite(alwaysRun = true)
	public void AfterSuite()
	{
		System.out.println("=============Closing Connection with DB==============");
	}
}
