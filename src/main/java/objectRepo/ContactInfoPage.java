package objectRepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactInfoPage 
{
	public ContactInfoPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(className="dvtSelectedCell")
	private WebElement contactInfoPageText;
	
	@FindBy(id="dtlview_First Name")
	private WebElement fnameEle;
	
	@FindBy(id="dtlview_Last Name")
	private WebElement lnameEle;
	
	@FindBy(id="dtlview_Mobile")
	private WebElement mobileEle;
	
	@FindBy(xpath="//table[@class='small']//td[@id='mouseArea_Organization Name']/a")
	private WebElement orgEle;
	
	@FindBy(xpath="//a[text()='Opportunities']/parent::td[@class='tabUnSelected']")
	private WebElement toOpportunityPageLink;

	public WebElement getContactInfoPageText() {
		return contactInfoPageText;
	}

	public WebElement getFnameEle() {
		return fnameEle;
	}

	public WebElement getLnameEle() {
		return lnameEle;
	}

	public WebElement getMobileEle() {
		return mobileEle;
	}

	public WebElement getOrgEle() {
		return orgEle;
	}
	
	public WebElement getToOpportunityPageLink() {
		return toOpportunityPageLink;
	}
}
