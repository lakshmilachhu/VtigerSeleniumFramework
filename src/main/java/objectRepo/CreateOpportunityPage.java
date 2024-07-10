package objectRepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateOpportunityPage 
{
	public CreateOpportunityPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//span[@class='lvtHeaderText']")
	private WebElement creatingOpportunityText;
	
	@FindBy(name="potentialname")
	private WebElement opportunityNameEle;
	
	@FindBy(id="related_to_type")
	private WebElement relatedDropdownEle;
	
	@FindBy(xpath="//select[@id='related_to_type']/../following-sibling::td/img")
	private WebElement addingSourceImgEle;
	
	@FindBy(name="closingdate")
	private WebElement calenderTextEle;
	
	@FindBy(name="button")
	private WebElement saveBtn;

	@FindBy(className="crmbutton small cancel")
	private WebElement cancelBtn;
	
	public WebElement getCreatingOpportunityText() {
		return creatingOpportunityText;
	}

	public WebElement getOpportunityNameEle() {
		return opportunityNameEle;
	}

	public WebElement getRelatedDropdownEle() {
		return relatedDropdownEle;
	}

	public WebElement getAddingSourceImgEle() {
		return addingSourceImgEle;
	}

	public WebElement getCalenderTextEle() {
		return calenderTextEle;
	}

	public WebElement getSaveBtn() {
		return saveBtn;
	}
	
	public WebElement getCancelBtn() {
		return cancelBtn;
	}
	
	public void enterOpportunity(String opportunityName)
	{
		opportunityNameEle.sendKeys(opportunityName);
	}
	
	public void enterClosingDate(String date)
	{
		calenderTextEle.clear();
		calenderTextEle.sendKeys(date);
	}
}
