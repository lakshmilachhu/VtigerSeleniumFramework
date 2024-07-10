package objectRepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrgInfoPage 
{
	public OrgInfoPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(className="dvtSelectedCell")
	private WebElement orgInfoPageText;
	
	@FindBy(xpath="//span[@class='dvHeaderText']")
	private WebElement visibleText;
	
	@FindBy(xpath="//span[@id='dtlview_Organization Name']")
	private WebElement orgEle;
	
	@FindBy(id="dtlview_Website")
	private WebElement websiteEle;
	
	@FindBy(xpath="//table[@class='hdrTabBg']//a[text()='Contacts']")
	private WebElement toContactPageLink;
	
	@FindBy(xpath="//a[text()='Opportunities']/parent::td[@class='tabUnSelected']")
	private WebElement toOpportunityPageLink;
	
	public WebElement getOrgInfoPageText() {
		return orgInfoPageText;
	}

	public WebElement getVisibleText() {
		return visibleText;
	}

	public WebElement getOrgEle() {
		return orgEle;
	}

	public WebElement getWebsiteEle() {
		return websiteEle;
	}
	
	public WebElement gettoContactPageLink() {
		return toContactPageLink;
	}

	public WebElement getToOpportunityPageLink() {
		return toOpportunityPageLink;
	}
}
