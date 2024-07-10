package objectRepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OpportunitiesPage 
{
	public OpportunitiesPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(className="hdrLink")
	private WebElement opportunityTextEle;
	
	@FindBy(xpath="//img[@title='Create Opportunity...']")
	private WebElement createOpportunityEle;
	
	@FindBy(xpath="//div[@id='searchAcc']//span[@class='moduleName']")
	private WebElement searchPageText;
	
	@FindBy(name="search_text")
	private WebElement searchingEle;
	
	@FindBy(xpath="search_field")
	private WebElement categoryDropdownEle;
	
	@FindBy(name="submit")
	private WebElement searchNowEle;
	
	@FindBy(xpath="//a[text()='del'")
	private WebElement deletEle;
	
	@FindBy(xpath="//span[@class='genHeaderSmall']")
	private WebElement verificationDeletText;
	
	@FindBy(xpath="//div[@id='status']/img")
	private WebElement loadingImg;

	public WebElement getOpportunityTextEle() {
		return opportunityTextEle;
	}

	public WebElement getCreateOpportunityEle() {
		return createOpportunityEle;
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
}
