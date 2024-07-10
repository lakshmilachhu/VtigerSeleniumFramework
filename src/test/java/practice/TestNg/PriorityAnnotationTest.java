package practice.TestNg;

import org.testng.annotations.Test;

public class PriorityAnnotationTest 
{
	//When we want to execute TestNg class, based on order of execution we'll use priority
	//we can initialize priority value from negative value to positiove ex-- -1, -2, -3 or 0 or 1,2,3 etc
	
	@Test(priority=1)
	public void createContactTest()
	{
		System.out.println("Creationg contacts with ICICI bank");
	}
	
	@Test(priority=2)
	public void modifiyingContactTest()
	{
		System.out.println("Modifiying ICICI contacts with HDFC contacts");
	}
	
	@Test(priority=3)
	public void deleteContactTest()
	{
		System.out.println("Deleting HDFC contatcs");
	}

}
