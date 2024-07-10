package objectRepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage 						//step1: create individual classes for each page
{
	public LoginPage(WebDriver driver)		//step3: declare PageFactory.initElements() in constructor to initialize
	{										//       the all Pageelements at the time of object creation
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(name="user_name")				//step2: identify all elements
	private WebElement userNameEle;
	
	@FindBy(name="user_password")
	private WebElement passwordEle;
	
	@FindBy(id="submitButton")
	private WebElement loginBtnEle;

	// step4 & 5: declare all element as private and provide getters method, business method for utilization
	public WebElement getUserNameEle() {
		return userNameEle;
	}

	public WebElement getPasswordEle() {
		return passwordEle;
	}

	public WebElement getLoginBtnEle() {
		return loginBtnEle;
	}
	
	public void toLoginApp(String userName,String password)
	{
		userNameEle.sendKeys("admin");
		passwordEle.sendKeys("admin");
		loginBtnEle.click();
	}

}
