package practice.mavenPractce;

import org.testng.annotations.Test;

public class Testng3Test {
	
	
	@Test
	public void test1() {
		String x=System.getProperty("envirn","mohan");
		System.out.println("3 ==> Test1");
		System.out.println("Envir ==> "+x);
	}
	
	@Test
	public void test2() {
		System.out.println("3 ==> Test2");
	}

}
