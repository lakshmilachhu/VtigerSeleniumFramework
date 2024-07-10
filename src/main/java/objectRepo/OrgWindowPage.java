package objectRepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrgWindowPage 
{
	public OrgWindowPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(className="moduleName")
	private WebElement orgWindowTitleEle;
	
	@FindBy(xpath="//input[@id='search_txt']")
	private WebElement searchBoxEle;
	
	@FindBy(name="search_field")
	private WebElement searchFieldEle;
	
	@FindBy(name="search")
	private WebElement searchBtnElement;
	
	@FindBy(xpath="//div[@id='status']/img")
	private WebElement loadingImgEle;
	
	@FindBy(xpath="//a[contains(text(),'Organization Name')]/ancestor::tr[1]/following-sibling::tr[1]/td[1]/a")
	private WebElement searchedOutputEle;

	public WebElement getOrgWindowTitleEle() {
		return orgWindowTitleEle;
	}

	public WebElement getSearchBoxEle() {
		return searchBoxEle;
	}

	public WebElement getSearchFieldEle() {
		return searchFieldEle;
	}

	public WebElement getSearchBtnElement() {
		return searchBtnElement;
	}

	public WebElement getLoadingImgEle() {
		return loadingImgEle;
	}

	public WebElement getSearchedOutputEle() {
		return searchedOutputEle;
	}
	
	public void searchOrg(String orgName)
	{
		searchBoxEle.sendKeys(orgName);
		searchBtnElement.click();
	}
}
