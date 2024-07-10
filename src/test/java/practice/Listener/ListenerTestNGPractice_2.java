package practice.Listener;

import org.testng.ITestContext;
import org.testng.annotations.Test;

public class ListenerTestNGPractice_2 {
	
	@Test
	public void test2(ITestContext cont) {
		System.out.println("@Test-2");
		System.out.println(cont.getAttribute("name"));
	}
	
	
	
}
