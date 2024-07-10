package objectRepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage 
{
	WebDriver driver;
	
	public HomePage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//a[@class='hdrLink']")
	private WebElement homeTextEle;
	
	@FindBy(linkText="Contacts")
	private WebElement contactLink;
	
	@FindBy(linkText="Organizations")
	private WebElement orgLink;
	
	@FindBy(linkText="Leads")	//instead of xpath, we can mention linkText (xpath="//td[@class='tabUnSelected']/a[text()='Leads']")
	private WebElement leadsLink;
	
	@FindBy(linkText="Opportunities")
	private WebElement opportunityLink;

	@FindBy(xpath="//span[normalize-space(text())='Administrator']/../following-sibling::td[1]/img")
	private WebElement administratorEle;
	
	@FindBy(linkText="Sign Out")
	private WebElement signoutLink;

	public WebElement getHomeTextEle() {
		return homeTextEle;
	}

	public WebElement getContactLink() {
		return contactLink;
	}

	public WebElement getOrgLink() {
		return orgLink;
	}
	
	public WebElement getLeadsLink() {
		return leadsLink;
	}

	public WebElement getOpportunityLink() {
		return opportunityLink;
	}

	public WebElement getAdministratorEle() {
		return administratorEle;
	}

	public WebElement getSignoutLink() {
		return signoutLink;
	}
	
	public void logout()
	{
		Actions action=new Actions(driver);
		action.moveToElement(administratorEle).perform();
		signoutLink.click();
	}
}
