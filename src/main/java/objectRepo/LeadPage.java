package objectRepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LeadPage 
{
	public LeadPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//a[@class='hdrLink']")
	private WebElement leadTextEle;
	
	@FindBy(xpath="//img[@title='Create Lead...']")
	private WebElement createLeadEle;
	
	@FindBy(xpath="//div[@id='searchAcc']//span[@class='moduleName']")
	private WebElement searchPageText;
	
	@FindBy(name="search_text")
	private WebElement searchingEle;
	
	@FindBy(name="search_field")
	private WebElement categoryDropdownEle;
	
	@FindBy(name="submit")
	private WebElement searchNowEle;
	
	@FindBy(xpath="//a[text()='del']")
	private WebElement deletEle;
	
	@FindBy(xpath="//span[@class='genHeaderSmall']")
	private WebElement verificationDeletText;
	
	@FindBy(xpath="//div[@id='status']/img")
	private WebElement loadingImg;

	public WebElement getLeadTextEle() {
		return leadTextEle;
	}

	public WebElement getCreateLeadEle() {
		return createLeadEle;
	}

	public WebElement getSearchPageText() {
		return searchPageText;
	}

	public WebElement getSearchingEle() {
		return searchingEle;
	}

	public WebElement getcategoryDropdownEle() {
		return categoryDropdownEle;
	}

	public WebElement getSearchNowEle() {
		return searchNowEle;
	}

	public WebElement getDeletEle() {
		return deletEle;
	}

	public WebElement getVerificationDeletText() {
		return verificationDeletText;
	}
	
	public WebElement getLoadingImg() {
		return loadingImg;
	}
	
	public void searchLead(String fname)
	{
		searchingEle.sendKeys(fname);
	}
}
