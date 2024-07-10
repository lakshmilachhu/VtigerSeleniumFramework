package practice.Listener;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ListenerTestNGPractice_1 {
	@BeforeClass
	public void se(ITestContext cont) {
		cont.setAttribute("name", "lakshmi-mohan");
	}
	
	
	@Test
	public void test1(ITestContext cont) {
		System.out.println("@Test-1");
	}
	
	
	
}
