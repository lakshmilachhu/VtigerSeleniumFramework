package objectRepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateContactPage 
{
	public CreateContactPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(className="lvtHeaderText")
	private WebElement createConPageText;
	
	@FindBy(name="firstname")
	private WebElement fnameEle;
	
	@FindBy(name="lastname")
	private WebElement lnameEle;
	
	@FindBy(name="mobile")
	private WebElement mobileEle;
	
	@FindBy(xpath="//td[normalize-space(.)='Organization Name']/following-sibling::td/img")
	private WebElement createOrgEle;
	
	@FindBy(xpath="//input[@class='crmButton small save']")
	private WebElement saveBtn;
	
	@FindBy(className="crmButton small cancel")
	private WebElement cancelBtn;
	
	public WebElement getCreateConPageText() {
		return createConPageText;
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

	public WebElement getCreateOrgEle() {
		return createOrgEle;
	}

	public WebElement getSaveBtn() {
		return saveBtn;
	}

	public WebElement getCancelBtn() {
		return cancelBtn;
	}

	public void createContact(String fname, String lname, String mobileNum)
	{
		fnameEle.sendKeys(fname);
		lnameEle.sendKeys(lname);
		mobileEle.sendKeys(mobileNum);
	}
}
