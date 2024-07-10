package practice.Listener;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ListenerTestNGPractice {
	@BeforeSuite
	public void bs() {
		System.out.println("@BeforeSuite"+Thread.currentThread().getId());
	}
	
	@BeforeTest
	public void bt() {
		System.out.println("@BeforeTest"+Thread.currentThread().getId());
	}
	
	@BeforeClass
	public void bc() {
		System.out.println("@BeforeClass"+Thread.currentThread().getId());
	}
	
	@BeforeMethod
	public void bm() {
		System.out.println("@BeforeMethod"+Thread.currentThread().getId());
	}
	
	@Test
	public void test1() {
		System.out.println("@Test-1 ==> "+Thread.currentThread().getId());
	}
	
	@Test
	public void test2() {
		System.out.println("@Test-2 ==> "+Thread.currentThread().getId());
	}
	
	@AfterMethod
	public void am(ITestResult result) {
		System.out.println(result.getStatus()==result.SUCCESS);
		System.out.println(result.getStatus()==result.FAILURE);
		System.out.println(result.getStatus()==result.SKIP);
		System.out.println("@AfterMethod"+Thread.currentThread().getId());
	}
	
	@AfterClass
	public void ac() {
		System.out.println("@AfterClass"+Thread.currentThread().getId());
	}
	
	@AfterTest
	public void at() {
		System.out.println("@AfterTest"+Thread.currentThread().getId());
	}
	
	@AfterSuite
	public void as() {
		System.out.println("@AfterSuite"+Thread.currentThread().getId());
	}
	
}
