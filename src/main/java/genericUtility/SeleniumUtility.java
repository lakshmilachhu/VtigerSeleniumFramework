package genericUtility;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import objectUtlity.TransferObject;

/**
 * this class contains selenium specific utilities
 * @author Lakshmi
 *
 */
public class SeleniumUtility 
{	
	private WebDriver driver=null;
	private Actions action;
	private WebDriverWait wait;
	private Select sect;
	/**
	 * this method is used launch the browser
	 * @param key
	 * @return driver
	 */
	public WebDriver launchBroswer(String key)
	{
		key=key.trim().toLowerCase();
		while(key!=null)
		{
			if(key.equalsIgnoreCase("chrome"))
			{
				System.setProperty("webdriver.chrome.driver",IConstants.chromePath);
				driver=new ChromeDriver();
				break;
			}
			else if(key.equalsIgnoreCase("fireFox")|| key.equalsIgnoreCase("mozilla"))
			{
				System.setProperty("webdriver.gecko.driver",IConstants.firefoxPath);
				driver=new FirefoxDriver();
				break;
			}
			else if(key.equalsIgnoreCase("ie"))
			{
				System.setProperty("webdriver.ie.driver",IConstants.iePath);
				driver=new InternetExplorerDriver();
				break;
			}
			else if(key.equalsIgnoreCase("edge"))
			{
				System.setProperty("webdriver.edge.driver", IConstants.edgePath);
				driver=new EdgeDriver();
				break;
			}
			else
				throw new RuntimeException("Give a valid broswer name");
		}
		return driver;
	}

	/**
	 * this method is used maximize the broswer
	 */
	public void maxBrowser()
	{
		driver.manage().window().maximize();
	}

	/**
	 * this method is used to launch the application
	 * by maximizing the screen
	 * @param url
	 */
	public void launchApp_MaxScreen(String url)
	{
		maxBrowser();
		driver.get(url);
	}

	/**
	 * this method is used to launch the application
	 * @param url
	 */
	public void launchApp(String url)
	{
		driver.get(url);
	}

	/**
	 * this method is used to navigate to the application
	 * @param url
	 */
	public void navigateApp(String url)
	{
		driver.navigate().to(url);
	}
	
	/** 
	 * this method is used to add implicit wait
	 * @param time
	 * @param unit
	 */
	public void addImplicitWait(long time, String unit)
	{
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	/**
	 * it is used add explicitwait
	 * @param driver
	 * @param time
	 * @return
	 */
	public WebDriverWait initExplicitWait(long time)
	{
		wait=new WebDriverWait(driver, time);
		return wait;
	}
	
	/**
	 * it is used to  Wait for the invisibility of an element 
	 * located by a specific locator
	 * @param wait
	 * @param locator
	 * @return
	 */
	public Boolean waitForElementInvisibility(By locator) 
	{
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

	/**
	 * it is used to Wait for the Invisibility of a specific element
	 * @param wait
	 * @param element
	 * @return
	 */
    public Boolean waitForElementInvisibility(WebElement element) 
    {
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }
    
    /**
     * it is used to wait for the visibility of a specific element
     * @param element
     * @return 
     */
    public WebElement waitForElementVisibility(WebElement element)
    {
    	return wait.until(ExpectedConditions.visibilityOf(element));
    }
    
	/**
	 * it is used to get the current url of the application
	 * @return
	 */
	public String getCurrentUrl()
	{
		return driver.getCurrentUrl();
	}

	/**
	 * it is used to get title of the webpage
	 * @return
	 */
	public String getTitle()
	{
		return driver.getTitle();
	}

	/**
	 * it is used to close current tab
	 */
	public void closeTab()
	{
		driver.close();
	}

	/**
	 * it is used to close the browser
	 */
	public void closeBrwoser()
	{
		driver.quit();
	}

	/**
	 * this method is used to re-size the broswer
	 * @param width
	 * @param height
	 */
	public void resizeBrowser(int width, int height)
	{
		driver.manage().window().setSize(new Dimension(width, height));
	}
	
	/**
	 * it is used to navigate back to previous webpage
	 */
	public void navigateBack()
	{
		driver.navigate().back();
	}
	
	/**
	 * it is used to navigate forward to next webpage
	 */
	public void navigateForward()
	{
		driver.navigate().forward();
	}
	
	/**
	 * it is used to refresh the webpage
	 */
	public void refreshWebPage()
	{
		driver.navigate().refresh();
	}
	
	/**
	 * this method is used to switch the driver to new window
	 * by passing partial url
	 * @param driver
	 * @param partialUrl
	 * @return
	 */
	public WebDriver switchToNewWindow_Url(String partialUrl)
	{
		Set<String> set=driver.getWindowHandles();
		for(String word:set)
		{
			driver.switchTo().window(word);
			if(driver.getCurrentUrl().contains(partialUrl))
				break;
		}
		return driver;
	}

	/**
	 * this method is used to switching driver to new window
	 * by passing title of the window
	 * @param title
	 * @param driver
	 * @return
	 */
	public WebDriver switchToNewWindow_Title(String title)
	{
		Set<String> set=driver.getWindowHandles();
		for(String word:set)
		{
			driver.switchTo().window(word);
			if(driver.getTitle().equalsIgnoreCase(title))
				break;
		}
		return driver;
	}

	/**
	 * this method is used to switch to the alert
	 * @param driver
	 * @return
	 */
	public Alert switchtoAlert(WebDriver driver)
	{
		return driver.switchTo().alert();
	}

	/**
	 * it is used to get notification of alert
	 * @param alert
	 * @return
	 */
	public String getAlertText(Alert alert)
	{
		return alert.getText();
	}
	/**
	 * this is used to accept the alert popup
	 * @param alert
	 */
	public void acceptAlert(Alert alert)
	{
		alert.accept();
	}

	/**
	 * this is used to dismiss the alert popup
	 * @param alert
	 */
	public void dismissAlert(Alert alert)
	{
		alert.dismiss();
	}

	/**
	 * this is used to send a data to the alert
	 * @param alert
	 * @param data
	 */
	public void sendDataToAlert(Alert alert, String data)
	{
		alert.sendKeys(data);
	}
	
	/**
	 * it is used to switch the frame 
	 * base on index
	 * @param index
	 * @return
	 */
	public WebDriver switchToFrame(int index)
	{
		return driver.switchTo().frame(index);
	}
	
	/**
	 * it is used to switch the frame
	 * based on name or id
	 * @param nameorid
	 * @return
	 */
	public WebDriver switchToFrame(String nameorid)
	{
		return driver.switchTo().frame(nameorid);
	}
	
	/**
	 * it is used to switch the frame
	 * based on element
	 * @param frameElement
	 * @return
	 */
	public WebDriver switchToFrame(WebElement frameElement)
	{
		return driver.switchTo().frame(frameElement);
	}
	
	/**
	 * it is used switch the frame 
	 * to parent frame defaulty
	 * @return
	 */
	public WebDriver switchToParentFrame()
	{
		return driver.switchTo().parentFrame();
	}
	
	/**
	 * this method is used luanch the chrome
	 * by disabling the notifications
	 */
	public void launchChrome_ChromeOption()
	{
		ChromeOptions optn=new ChromeOptions();
		optn.addArguments("--disable-notifications");
		driver=new ChromeDriver(optn);
	}
	
	/**
	 * it is used to initialize the Actions class
	 * @param driver
	 * @return
	 */
	public Actions initActions(WebDriver driver)
	{
		action=new Actions(driver);
		return action;
	}
	
	/**
	 * its used to drag and drop the element from source to target
	 * @param source
	 * @param target
	 */
	public void drag_Drop(WebElement source, WebElement target)
	{
		action.dragAndDrop(source, target).perform();;
	}
	
	/**
	 * its used to move the cursor to sepecified element
	 * @param element
	 */
	public void move_Element(WebElement element)
	{
		action.moveToElement(element).perform();;
	}
	
	/**
	 * it is used to click on webpage randomly
	 */
	public void click()
	{
		action.click().perform();;
	}
	
	/**
	 * its used to click the cursor on sepecified element
	 * @param element
	 */
	public void click_Element(WebElement element)
	{
		action.click(element).perform();;
	}
	
	/**
	 * its used to click and hold on random space over webpage
	 */
	public void click_Hold()
	{
		action.clickAndHold().perform();;
	}
	
	/**
	 * its used to click and hold on particular element
	 * @param element
	 */
	public void click_Hold(WebElement element)
	{
		action.clickAndHold(element).perform();;
	}
	
	/**
	 * its used to double click on random space
	 */
	public void double_Click()
	{
		action.doubleClick().perform();
	}
	
	/**
	 * its used to double click on particular element
	 * @param element
	 */
	public void double_Click(WebElement element)
	{
		action.doubleClick(element).perform();;
	}
	
	/**
	 * its used to right click
	 */
	public void context_Click()
	{
		action.contextClick().perform();
	}
	
	/**
	 * its used to right click on element
	 * @param element
	 */
	public void context_Click(WebElement element)
	{
		action.contextClick(element).perform();
	}
	
	/**
	 * it is used to initialize the select class
	 * @param element
	 */
	public void initSelect(WebElement element)
	{
		sect=new Select(element);
	}
	
	/**
	 * its used to select the option by giving value of the element
	 * @param valueData
	 */
	public void selectByValue(String valueData)
	{
		sect.selectByValue(valueData);
	}
	
	/**
	 * its used to select the option by giving index of the element
	 * @param valueData
	 */
	public void selectByIndex(int index)
	{
		sect.selectByIndex(index);
	}
	
	/**
	 * its used to select the option by giving visible text of the element
	 * @param valueData
	 */
	public void selectByText(String text)
	{
		sect.selectByVisibleText(text);
	}
	
	/**
	 * its used to deselect the option by giving value of the element
	 * @param valueData
	 */
	public void deselectByValue(String valueData)
	{
		sect.deselectByValue(valueData);
	}
	
	/**
	 * its used to deselect the option by giving index of the element
	 * @param valueData
	 */
	public void deselectByIndex(int index)
	{
		sect.deselectByIndex(index);
	}
	
	/**
	 * its used to deselect the option by giving visible text of the element
	 * @param valueData
	 */
	public void deselectByText(String text)
	{
		sect.deselectByVisibleText(text);
	}
	
	/**
	 * its used to get options presented in the dropdown
	 * @return
	 */
	public List<WebElement> getAllOptions()
	{
		return sect.getOptions();
	}
	
	/**
	 * its used to check whether the dropdown is multi selected or not
	 * @return
	 */
	public boolean isMultiSelectDropdown()
	{
		return sect.isMultiple();
	}
	
	/**
	 * its used to take a screenshot
	 * @param fileName
	 * @throws IOException
	 */
	public void getScreenshot(String fileName) throws IOException
	{
		TakesScreenshot screen= (TakesScreenshot)TransferObject.getDriver();
		File srcfile=screen.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcfile, new File(fileName));
	}
}
