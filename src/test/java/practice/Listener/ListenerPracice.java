package practice.Listener;

import org.testng.IClassListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerPracice implements ISuiteListener,IClassListener, ITestListener{

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("onFinish --> test" +Thread.currentThread().getId());
	}

	@Override
	public void onStart(ITestContext context) {
		System.out.println("onStart --> test"+Thread.currentThread().getId());
		}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("onTestFailedButWithinSuccessPercentage"+Thread.currentThread().getId());
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		System.out.println("onTestFailedWithTimeout"+Thread.currentThread().getId());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		System.out.println("onTestFailure"+Thread.currentThread().getId());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("onTestSkipped"+Thread.currentThread().getId());
	}

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("onTestStart"+Thread.currentThread().getId());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println(result.getMethod().getRealClass().getSimpleName());
		System.out.println(result.getMethod().getMethodName());
		System.out.println("onTestSuccess"+Thread.currentThread().getId());
	}

	@Override
	public void onAfterClass(ITestClass testClass) {
		System.out.println("onAfterClass --> class"+Thread.currentThread().getId());
	}

	@Override
	public void onBeforeClass(ITestClass testClass) {
		System.out.println("onBeforeClass --> class"+Thread.currentThread().getId());
	}

	@Override
	public void onFinish(ISuite suite) {
		System.out.println("onFinish --> Suite"+Thread.currentThread().getId());
	}

	@Override
	public void onStart(ISuite suite) {
		System.out.println("onStart --> Suite"+Thread.currentThread().getId());
	}

	
	
	
}
