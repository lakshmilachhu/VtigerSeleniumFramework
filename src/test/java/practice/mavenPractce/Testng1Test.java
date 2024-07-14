package practice.mavenPractce;

import org.testng.annotations.Test;

public class Testng1Test {
	//c3
	//c2
	//c1
	@Test
	public void test1() {
		String env=System.getProperty("envirn");
		System.out.println("1 ==> Test1");
		System.out.println("Env == > "+env);
	}
	
	@Test
	public void test2() {
		String browser=System.getProperty("bro");
		System.out.println("1 ==> Test2");

		System.out.println("Browser == > "+browser);
	}

}
