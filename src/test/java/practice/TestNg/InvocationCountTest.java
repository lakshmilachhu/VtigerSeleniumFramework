package practice.TestNg;

import org.testng.annotations.Test;

public class InvocationCountTest 
{
	//This InvocationCount annotation is used to execute a same script multiple times with same data
	@Test(invocationCount=10)
	public void printData()
	{
		System.out.println("Ohm Gam Ganapathiye Namha");
	}

}
