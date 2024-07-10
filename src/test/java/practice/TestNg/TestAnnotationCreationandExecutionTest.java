package practice.TestNg;

import org.testng.annotations.Test;

public class TestAnnotationCreationandExecutionTest 
{
	//@Test annotation will runs based on ASCII
	//In single class we can have n no of @Test annotations
	//if we didn't added @Test annotation to any method, it'll consider as normal method of Java
	//@Test annotation is act like a main method in Java
	//@Test annotation's return type should alsways void 
	//and it's access specifier will be anything but if it's public that's good to process
	
	@Test
	public void createContactTest()
	{
		System.out.println("Creationg contacts with ICICI bank");
	}
	
	@Test              
	public void modifiyingContactTest()
	{
		System.out.println("Modifiying ICICI contacts with HDFC contacts");
	}
	
	@Test
	public void deleteContactTest()
	{
		System.out.println("Deleting HDFC contatcs");
	}
	
	

}
