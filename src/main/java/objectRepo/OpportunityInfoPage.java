package objectRepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OpportunityInfoPage 
{
	public OpportunityInfoPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//td[@class='dvtSelectedCell']")
	private WebElement opportunityInfoPageText;
	
	@FindBy(id="mouseArea_Opportunity Name")
	private WebElement opportunityNameText;
	
	@FindBy(xpath="//td[@class='dvtCellInfo']/a")
	private WebElement relatedLinkEle;
	
	@FindBy(id="mouseArea_Organization Name")
	private WebElement traverseToSelectedOrg;
	
	@FindBy(xpath="//a[@title='Contacts']")
	private WebElement traverseToSelectedContact;

	@FindBy(xpath="//a[text()='Opportunities']/parent::td[@class='tabUnSelected']")
	private WebElement toOpportunityPageLink;
	
	public WebElement getOpportunityInfoPageText() {
		return opportunityInfoPageText;
	}

	public WebElement getOpportunityNameText() {
		return opportunityNameText;
	}

	public WebElement getRelatedLinkEle() {
		return relatedLinkEle;
	}

	public WebElement getTraverseToSelectedOrg() {
		return traverseToSelectedOrg;
	}

	public WebElement getTraverseToSelectedContact() {
		return traverseToSelectedContact;
	}

	public WebElement getToOpportunityPageLink() {
		return toOpportunityPageLink;
	}
}
