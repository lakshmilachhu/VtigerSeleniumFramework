package objectRepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactsPage 
{
	public ContactsPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//a[@class='hdrLink']")
	private WebElement contactTextEle;
	
	@FindBy(xpath="//img[@title='Create Contact...']")
	private WebElement createContactLink;
	
	@FindBy(xpath="//div[@id='searchAcc']//span[@class='moduleName']")
	private WebElement searchPageText;
	
	@FindBy(xpath="//input[@class='txtBox']")
	private WebElement searchingEle;
	
	@FindBy(name="search_field")
	private WebElement categoryDropdwonEle;
	
	@FindBy(name="submit")
	private WebElement searchNowEle;
	
	@FindBy(xpath="//div[@id='status']/img")
	private WebElement loadingImg;
	
	@FindBy(xpath="//table[@class='lvt small']//a[text()='del']")
	private WebElement deletEle;
	
	@FindBy(xpath="//span[@class='genHeaderSmall']")
	private WebElement verificationDeletText;

	public WebElement getContactTextEle() {
		return contactTextEle;
	}

	public WebElement getCreateContactLink() {
		return createContactLink;
	}

	public WebElement getSearchPageText() {
		return searchPageText;
	}

	public WebElement getSearchingEle() {
		return searchingEle;
	}

	public WebElement getCategoryDropdwonEle() {
		return categoryDropdwonEle;
	}

	public WebElement getSearchNowEle() {
		return searchNowEle;
	}
	
	public WebElement getLoadingImg() {
		return loadingImg;
	}

	public WebElement getDeletEle() {
		return deletEle;
	}

	public WebElement getVerificationDeletText() {
		return verificationDeletText;
	}
	
	public void enterSearchtext(String lastName)
	{
		searchingEle.sendKeys(lastName);
	}
	
}
