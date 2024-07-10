package practice.Listener;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import baseClassTestng.BaseClassTestNg;

@Listeners(listenerImplementation.ListenImplement.class)
public class ListenerTest_Failure extends BaseClassTestNg
{
	@Test
	public void createInvoiceTest()
	{
		System.out.println("Example for onTestFailure and to get screenshot");
		
		String actTitle=driver.getTitle();
		Assert.assertEquals(actTitle, "Login");
		
		System.out.println("Step1");
		System.out.println("Step2");
	}
	
	@Test
	public void createInvoiceTest2()
	{
		System.out.println("Execute the createInvoiceTest2");
		System.out.println("Step1");
		System.out.println("Step2");
		System.out.println("Step3");
	}
}
