package practice.extentReport;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class SampleReportTest2 
{
	ExtentReports report;
	
	@BeforeSuite
	public void configBS()
	{
		//Step1: get instance of ExtentSparkReporter and Configure the report
		ExtentSparkReporter spark=new ExtentSparkReporter("./AdvanceReport/report.html");
		spark.config().setDocumentTitle("CRM TestSuite Report");
		spark.config().setReportName("CRM Report");
		spark.config().setTheme(Theme.STANDARD);

		//Step2: Add Env information 
		report=new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("OS", "Windows-10");
		report.setSystemInfo("BROWSER", "chrome");
	}

	@AfterSuite
	public void configAS()
	{
		//Step4: save the log
		report.flush();
	}
	
	@Test
	public void createContatctTest()
	{
		//Step3: By creating test, get the instance of ExtentTest to generate the log
		ExtentTest test=report.createTest("createContatctTest");

		test.log(Status.INFO,"FirstName : Lakshmi");
		test.log(Status.INFO,"LastName : Mohan");
		int age=26;
		if(age>=18)
			test.log(Status.PASS,"Pass: Age is preffered to get Marry");
		else
			test.log(Status.FAIL,"Fail: Age is not preffered to get Marry");
	}
	
	@Test
	public void createContactWithPhonenumTest()
	{
		ExtentTest test=report.createTest("createContactWithPhonenumTest");
		test.log(Status.INFO,"PhoneNum : 8050827720");
	}
	
	@Test
	public void createContactWithOrgTest()
	{
		ExtentTest test=report.createTest("createContactWithOrgTest");
		test.log(Status.SKIP, "Planning to change the job");
	}
}
