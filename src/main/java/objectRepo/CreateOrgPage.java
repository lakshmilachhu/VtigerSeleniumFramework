package objectRepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateOrgPage 
{
	public CreateOrgPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//span[@class='lvtHeaderText']")
	private WebElement createOrgPageText;
	
	@FindBy(name="accountname")
	private WebElement orgEle;

	@FindBy(name="website")
	private WebElement websiteEle;
	
	@FindBy(xpath="//input[@class='crmbutton small save']")
	private WebElement saveBtn;
	
	@FindBy(className="crmbutton small cancel")
	private WebElement cancelBtn;

	public WebElement getCreateOrgPageText() {
		return createOrgPageText;
	}

	public WebElement getOrgEle() {
		return orgEle;
	}

	public WebElement getWebsiteEle() {
		return websiteEle;
	}

	public WebElement getSaveBtn() {
		return saveBtn;
	}

	public WebElement getCancelBtn() {
		return cancelBtn;
	}

	public void createOrg(String orgName, String websiteName)
	{
		orgEle.sendKeys(orgName);
		websiteEle.sendKeys(websiteName);
	}
}





