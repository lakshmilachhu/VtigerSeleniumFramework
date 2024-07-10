package practice.Listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer
{
	int count=1;
	int limitCount=4;
	
	@Override
	public boolean retry(ITestResult result) 
	{
		if(count<=limitCount)
		{
			count++;
			return true;
		}
		return false;
	}
}
