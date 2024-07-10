package practice.TestNg;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderTest 
{
	//In order to execute same test script with multiple data we'll go for DataProvider
	//It alsways return 2dimensional object array, because we can pass any type of datatype
	
	@Test (dataProvider = "dataProvider_BookedDataTest")
	public void bookedDataTest(String src, String dest)
	{
		System.out.println("Lakshmi & Mohan are travelling from " + src + " to " + dest);
	}
	
	@DataProvider
	public Object[][] dataProvider_BookedDataTest()
	{
		Object[][] objArr=new Object[4][2];
		
		objArr[0][0]="Banglore";
		objArr[0][1]="Dharmasthala";
		
		objArr[1][0]="Banglore";
		objArr[1][1]="Mysore";
		
		objArr[2][0]="Banglore";
		objArr[2][1]="Manali";
		
		objArr[3][0]="Banglore";
		objArr[3][1]="UK";
				
		return objArr;
	}
}
