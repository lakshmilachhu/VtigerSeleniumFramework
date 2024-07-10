package practice.TestNg;

import org.testng.annotations.Test;

public class DependsOnMethodAnnotationTest 
{
	//DependsonMethod annotation which is used to check dependent test case is pass or fail
	//if dependent test script get pass, execution will continue
	//if dependent test script get fail, other script which is dependent on first test those will get skipped.
	
	@Test
	public void createContactTest()
	{
		System.out.println("Creationg contacts with ICICI bank");
		String obj=null;
		System.out.println(obj.equals("123"));
	}
	
	@Test(dependsOnMethods = "createContactTest")
	public void modifiyingContactTest()
	{
		System.out.println("Modifiying ICICI contacts with HDFC contacts");
	}
	
	@Test(dependsOnMethods = "modifiyingContactTest")
	public void deleteContactTest()
	{
		System.out.println("Deleting HDFC contatcs");
	}

}
