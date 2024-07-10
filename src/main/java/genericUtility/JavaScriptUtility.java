package genericUtility;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
/**
 * this class contains Javascript utilities
 * @author Lakshmi
 *
 */
public class JavaScriptUtility 
{
	private JavascriptExecutor js;
	
	/**
	 * it is used to initialize the Javascript executor
	 * to driver by doing downcasting
	 * @param driver
	 * @return
	 */
	public void initJavaScriptExecutor(WebDriver driver)
	{
		js=(JavascriptExecutor)driver;	
	}
	
	/**
	 * it is used click the element
	 * @param js
	 * @param element
	 */
	public void toClickElement(WebElement element)
	{
		js.executeScript("arguments[0].click()", element);
	}
	
	/**
	 * it is used to scroll down the page
	 * till pixel 
	 * @param pixel
	 */
	public void scrollDownToPixel(int pixel)
	{
		js.executeScript("window.scrollBy(0, " + pixel + ")");
	}
	
	/**
	 * it is used to scroll up the page
	 * till pixel
	 * @param pixel
	 */
	public void scrollUpToPixel(int pixel)
	{
		js.executeScript("window.scrollTo(0," + pixel + ")");
	}
	
	/**
	 * it is used to scorll down
	 */
	public void scrollDownToPage()
	{
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}
	
	/**
	 * it is used to scroll up
	 */
	public void scrollUpToPage()
	{
		js.executeScript("window.scrollTo(0,0)");
	}

	/**
	 * it is used to scroll till element present
	 * @param element
	 */
	public void scrollIntoElement(WebElement element)
	{
		js.executeScript("arguments[0].scrollIntoView(true)", element);
	}
	
	/**
	 * it is used to get title of webpage
	 * @return
	 */
	public String getPageTitle()
	{
		String title=(String) js.executeScript("return document.title");
		return title;
	}
	
	/**
	 * it is used enter a text into text field
	 * @param element
	 * @param text
	 */
	public void setText(WebElement element, String text)
	{
		js.executeScript("arguments[0].value="+ text + "," , element);
	}
	
	/**
	 * it is used to reload the webpage
	 */
	public void pageReload()
	{
		js.executeScript("location.reload()");
	}
	
	/**
	 * it is used to log messages to browser
	 * @param msg
	 */
	public void consoleLogMsg(String msg)
	{
		js.executeScript("console.log(arguments[0])" , msg);
	}
	
	/**
	 * it is used log warning messages to browser
	 * @param msg
	 */
	public void consoleWarnMsg(String msg)
	{
		js.executeScript("console.warn(arguments[0])" , msg);
	}
	
	/**
	 * it is used to log error messages to browser
	 * @param msg
	 */
	public void consoleErrMsg(String msg)
	{
		js.executeScript("console.error(arguments[0])" , msg);
	}
}
