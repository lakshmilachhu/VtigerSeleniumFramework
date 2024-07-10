package practice.Listener;

import org.testng.annotations.Test;

public class ListerRetryTest 
{
	@Test(retryAnalyzer = RetryAnalyzer.class )
	public void configTest1()
	{
		System.out.println("Execute Test1");
		assert false: "Test1 failed";
	}

	@Test
	public void configTest2()
	{
		System.out.println("Execute Test2");
	}

	@Test
	public void configTest3()
	{
		System.out.println("Execute Test3");
	}

}
