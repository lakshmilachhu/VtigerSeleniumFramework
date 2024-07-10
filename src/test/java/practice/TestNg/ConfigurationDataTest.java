package practice.TestNg;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

//We can have n num of Configuration methods in TestNg class
//test methods will execute based on ASCII
//if multiple configuration methods is present, then it'll also execute based on ASCII
//there is no rule like, after utilizing "before_Suite,class,method methods" then only we should use "after_class,suite,method methods"
//we can use after_C,S,M methods without using before_C,S,M methods and vise versa...

public class ConfigurationDataTest 
{
	@Test
	public void testScript1()
	{
		System.out.println("Executed Testscript1");
	}
	
	@BeforeSuite
	public void beforeSuite1()
	{
		System.out.println("Executed BeforeSuite 1");
	}
	
	@BeforeMethod
	public void beforeMethod1()
	{
		System.out.println("Executed BeforeMethod 1");
	}
	
	@AfterMethod
	public void afterMethod()
	{
		System.out.println("Executed AfterMethod");
	}
	
	@BeforeMethod
	public void beforeMethod2()
	{
		System.out.println("Executed BeforeMethod 2");
	}
	@Test
	public void testScript2()
	{
		System.out.println("Executed Testscript2");
	}

	@AfterClass
	public void afterClass()
	{
		System.out.println("Executed AfterClass");
	}
	
	@BeforeClass
	public void beforeClass()
	{
		System.out.println("Executed BeforeClass");
	}
	
	@Test
	public void testScript3()
	{
		System.out.println("Executed Testscript3");
	}
	
	@AfterClass()
	public void afterClass2()
	{
		System.out.println("Executed AfterClass 2");
	}
	
	@BeforeSuite
	public void beforeSuite()
	{
		System.out.println("Executed BeforeSuite");
	}
	
	@AfterSuite
	public void afterSuite()
	{
		System.out.println("Executed AfterSuite");
	}
	
	@BeforeTest
	public void beforeTest()
	{
		System.out.println("Executed BeforeTest");
	}
	
	@AfterTest
	public void afterTest()
	{
		System.out.println("Executed AfterTest");
	}
}
