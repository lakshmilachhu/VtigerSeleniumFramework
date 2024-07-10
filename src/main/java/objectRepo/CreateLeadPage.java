package objectRepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateLeadPage 
{
	public CreateLeadPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//span[@class='lvtHeaderText']")
	private WebElement createLeadPageText;
	
	@FindBy(name="firstname")
	private WebElement fnameTextEle;
	
	@FindBy(name="lastname")
	private WebElement lnameTextEle;
	
	@FindBy(name="company")
	private WebElement companyTextEle;
	
	@FindBy(name="industry")
	private WebElement industryDropdownEle;
	
	@FindBy(name="leadsource")
	private WebElement leadsourceDropdownEle;
	
	@FindBy(xpath="//input[@class='crmButton small save']")
	private WebElement saveBtn;
	
	@FindBy(className="crmbutton small cancel")
	private WebElement cancelBtn;

	public WebElement getCreateLeadPageText() {
		return createLeadPageText;
	}

	public WebElement getFnameTextEle() {
		return fnameTextEle;
	}

	public WebElement getLnameTextEle() {
		return lnameTextEle;
	}

	public WebElement getCompanyTextEle() {
		return companyTextEle;
	}

	public WebElement getIndustryDropdownEle() {
		return industryDropdownEle;
	}

	public WebElement getLeadsourceDropdownEle() {
		return leadsourceDropdownEle;
	}

	public WebElement getSaveBtn() {
		return saveBtn;
	}
	
	public WebElement getCancelBtn() {
		return cancelBtn;
	}
	
	public void createLead(String fname, String lname, String company)
	{
		fnameTextEle.sendKeys(fname);
		lnameTextEle.sendKeys(lname);
		companyTextEle.sendKeys(company);
	}

}
