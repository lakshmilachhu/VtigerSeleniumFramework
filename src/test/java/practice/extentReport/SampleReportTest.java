package practice.extentReport;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class SampleReportTest 
{
	@Test
	public void createContatctTest()
	{
		//Step1: get instance of ExtentSparkReporter and Configure the report
		ExtentSparkReporter spark=new ExtentSparkReporter("./AdvanceReport/report.html");
		spark.config().setDocumentTitle("CRM Test Suite Resultes");
		spark.config().setReportName("CRM Report");
		spark.config().setTheme(Theme.DARK);
		
		//Step2: Add Env information 
		ExtentReports report=new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("BROWSER", "chrome");
		report.setSystemInfo("OS", "Windows-10");
		
		//Step3: By creating test, get the instance of ExtentTest to generate the log
		ExtentTest test=report.createTest("createContactTest");
			
		test.log(Status.INFO,"FirstName : Lakshmi");
		test.log(Status.INFO,"LastName : Mohan");
		int age=26;
		if(age>=18)
			test.log(Status.PASS,"Pass: Age is preffered to get Marry");
		else
			test.log(Status.FAIL,"Fail: Age is not preffered to get Marry");
		
		//Step4: save the log
		report.flush();
	}

}
