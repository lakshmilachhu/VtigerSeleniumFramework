package objectRepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LeadInfoPage 
{
	public LeadInfoPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//td[@class='dvtSelectedCell']")
	private WebElement leadInfoPageText;
	
	@FindBy(id="mouseArea_First Name")
	private WebElement fnameEle;
	
	@FindBy(id="mouseArea_Last Name")
	private WebElement lnameEle;
	
	@FindBy(id="mouseArea_Company")
	private WebElement companyEle;
	
	@FindBy(id="mouseArea_Industry")
	private WebElement industryEle;
	
	@FindBy(id="mouseArea_Lead Source")
	private WebElement leadSourceEle;
	
	@FindBy(xpath="//a[text()='Leads']/parent::td[@class='tabSelected']")
	private WebElement toLeadPageLink;

	public WebElement getLeadInfoPageText() {
		return leadInfoPageText;
	}

	public WebElement getFnameEle() {
		return fnameEle;
	}

	public WebElement getLnameEle() {
		return lnameEle;
	}

	public WebElement getCompanyEle() {
		return companyEle;
	}

	public WebElement getIndustryEle() {
		return industryEle;
	}

	public WebElement getLeadSourceEle() {
		return leadSourceEle;
	}

	public WebElement getToLeadPageLink() {
		return toLeadPageLink;
	}
}
