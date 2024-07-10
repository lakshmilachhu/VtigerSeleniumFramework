package listenerImplementation;

import java.io.IOException;

import org.testng.IClassListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import genericUtility.JavaUtility;
import genericUtility.SeleniumUtility;
import objectUtlity.TransferObject;


public class ListenImplement implements ISuiteListener, ITestListener, IClassListener
{
	private ExtentReports report;
	private ExtentTest test;
	JavaUtility javaLib=new JavaUtility();
	SeleniumUtility seleniumLib=new SeleniumUtility();

	@Override
	public void onStart(ISuite suite) 
	{
		String time=javaLib.getSystemDateTime("dd-MM-yyyy");
		
		//get instance of ExtentSparkReporter and Configure the report
		ExtentSparkReporter spark=new ExtentSparkReporter("./AdvanceReport/report_"+time);
		spark.config().setDocumentTitle("CRM TestSuite Report");
		spark.config().setReportName("CRM report");
		spark.config().setTheme(Theme.STANDARD);

		//Add Env information 
		report=new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("OS", "Windows-10");
		report.setSystemInfo("BROWSER", "chrome");
	}

	@Override
	public void onFinish(ISuite suite) 
	{
		//save the log
		report.flush();
	}

	@Override
	public void onStart(ITestContext context) 
	{
		System.out.println("onStart ---> Test-level");
	}

	@Override
	public void onFinish(ITestContext context) 
	{
		System.out.println("onFinish ---> Test-level");
	}

	@Override
	public void onBeforeClass(ITestClass testClass) 
	{
		System.out.println("onBeforeClass ---> Class-level "+ testClass.getRealClass().getSimpleName());
	}

	@Override
	public void onAfterClass(ITestClass testClass) 
	{
		System.out.println("onAfterClass ---> Class-level");
	}

	@Override
	public void onTestStart(ITestResult result) 
	{
		//creating test
		test=report.createTest(result.getMethod().getMethodName());
		
		//creating individual nodes for each @test methods
		test=test.createNode(result.getMethod().getMethodName());
		TransferObject.setTest(test); 
		
		test.log(Status.INFO, result.getMethod().getMethodName()+"STARTED");
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("onTestSuccess ---> test");
	}
	
	@Override
	public void onTestFailure(ITestResult result) 
	{
		String time=javaLib.getSystemDateTime("dd_MMMM_yyyy_HH_mm_sss");

		// getting a screenshot of failed testscripts
		try {
			seleniumLib.getScreenshot("./screenshot/"+result.getMethod().getMethodName()+"_"+time+".png");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public void onTestSkipped(ITestResult result) 
	{
		System.out.println("onTestSkipped ---> test");
		
		String time=javaLib.getSystemDateTime("dd_MMMM_yyyy_HH_mm_sss");

		// getting a screenshot of skipped testscripts
		try {
			seleniumLib.getScreenshot("./screenshot/"+result.getMethod().getMethodName()+"_"+time+".png");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) 
	{
		System.out.println("onTestFailedButWithinSuccessPercentage ---> test");
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) 
	{
		System.out.println("onTestFailedWithTimeout ---> test");
	}
}
