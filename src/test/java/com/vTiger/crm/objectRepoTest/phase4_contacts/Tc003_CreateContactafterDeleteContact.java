package com.vTiger.crm.objectRepoTest.phase4_contacts;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericUtility.ExcelUtility;
import genericUtility.IConstants;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.SeleniumUtility;
import objectRepo.ContactInfoPage;
import objectRepo.ContactsPage;
import objectRepo.CreateContactPage;
import objectRepo.HomePage;
import objectRepo.LoginPage;

public class Tc003_CreateContactafterDeleteContact 
{
	public static void main(String[] args) throws IOException
	{
		//creating instances for utilities
		PropertyFileUtility propertyLib=new PropertyFileUtility();
		ExcelUtility excelLib=new ExcelUtility();
		JavaUtility javaLib=new JavaUtility();
		SeleniumUtility seleniumLib=new SeleniumUtility();

		//accessing property file
		propertyLib.initPropertyFile(IConstants.propertyFilePath);

		String appUrl=propertyLib.getData("url");
		String userName=propertyLib.getData("userName");
		String password=propertyLib.getData("password");
		long timeout=Long.parseLong(propertyLib.getData("timeout"));

		//accessing excel file
		excelLib.initExcelFile(IConstants.excelFilePath);

		//fetching the data from Verification sheet
		Map<String,String> map1=excelLib.getAllHeaderAndValues_of_SpecifiedData("Verification","Verification_001");

		//fetching data from contact sheet
		Map<String,String> map2=excelLib.getAllHeaderAndValues_of_SpecifiedData("Contacts", "TC_001");

		map1.putAll(map2);

		String firstName= javaLib.getRandFname();
		String lastName=javaLib.getRandLname();
		String mobileNumber=javaLib.getRandMobNum();
		String loginBtnVeriTxt=map1.get("LoginBtn_Txt");
		String homePageVeriTxt=map1.get("HomePage_Txt");
		String contactPageVeriTxt=map1.get("ContactPage_Txt");
		String contactCreatePageTXT=map1.get("Contact_CreatePageText");
		String contact_InformationPageText=map1.get("Contact_InformationPageText");
		String searchVeriText=map1.get("Search_Box");
		String invalidPageMsg=map1.get("Invalid_Page_Message");
		String invalidDataMsg=map1.get("Invalid_Data_Message");

		//launching a browser	
		WebDriver driver=seleniumLib.launchBroswer("chrome");
		seleniumLib.maxBrowser();
		seleniumLib.addImplicitWait(timeout, "Seconds");

		//initializing explicit wait
		seleniumLib.initExplicitWait(timeout);

		//launching a application
		seleniumLib.launchApp(appUrl);

		LoginPage login=new LoginPage(driver);

		//verifying login page
		String actLoginBtnName=login.getLoginBtnEle().getAttribute("value");
		if(!actLoginBtnName.equalsIgnoreCase(loginBtnVeriTxt))
			throw new RuntimeException(String.format(invalidPageMsg, "Login"));		

		//login into the application by entering username and password	
		login.toLoginApp(userName, password);

		HomePage home=new HomePage(driver);

		//verifying home page
		String actHomepageName=home.getHomeTextEle().getText();
		if(!actHomepageName.trim().equalsIgnoreCase(homePageVeriTxt))
			throw new RuntimeException(String.format(invalidPageMsg, "Home"));

		//navigate into contacts
		home.getContactLink().click();

		ContactsPage contact=new ContactsPage(driver);

		//verifying contact page
		String actContactPage=contact.getContactTextEle().getText();	
		if(!actContactPage.equalsIgnoreCase(contactPageVeriTxt))
			throw new RuntimeException(String.format(invalidPageMsg, "Contact"));

		//navigate into create contacts
		contact.getCreateContactLink().click();

		CreateContactPage createCon=new CreateContactPage(driver);

		//verifying creating contact page
		String createPageName=createCon.getCreateConPageText().getText();		
		if(!createPageName.equalsIgnoreCase(contactCreatePageTXT))
			throw new RuntimeException(invalidPageMsg);

		//creating a contact by entering data
		createCon.createContact(firstName, lastName, mobileNumber);

		//saving the data
		createCon.getSaveBtn().click();

		ContactInfoPage verification=new ContactInfoPage(driver);

		//verifying contact information page
		String actContactInfoPageName=verification.getContactInfoPageText().getText();
		if(!actContactInfoPageName.equalsIgnoreCase(contact_InformationPageText))
			throw new RuntimeException(invalidPageMsg);

		//verifying the data
		String actFirstName=verification.getFnameEle().getText();
		if(actFirstName.equalsIgnoreCase(firstName))
			System.out.println("FirstName is: " + firstName);
		else
			throw new RuntimeException(invalidDataMsg);

		String actLastName=verification.getLnameEle().getText();
		if(actLastName.equalsIgnoreCase(lastName))
			System.out.println("LastName is: " + lastName);
		else
			throw new RuntimeException(invalidDataMsg);

		String actMobileNum=verification.getMobileEle().getText();
		if(actMobileNum.equalsIgnoreCase(mobileNumber))
			System.out.println("ContactNum is: " + mobileNumber);
		else
			throw new RuntimeException(invalidDataMsg);	

		//navigate back to contacts, to reach search contact page
		home.getContactLink().click();

		//verification search page
		String searchPageText=contact.getSearchPageText().getText();
		if(!searchPageText.contains(searchVeriText))
			throw new RuntimeException(invalidPageMsg);

		//entering required data	
		contact.enterSearchtext(lastName);

		//selecting the category
		WebElement searchContext=contact.getCategoryDropdwonEle();
		seleniumLib.initSelect(searchContext);
		seleniumLib.selectByValue("lastname");

		//searching now
		contact.getSearchNowEle().click();

		WebElement imgEle=contact.getLoadingImg();
		seleniumLib.waitForElementInvisibility(imgEle);

		//finding particular element to delete it
		contact.getDeletEle().click();

		//to get confirmation for deletion, we'll get popup notiification
		Alert alert=seleniumLib.switchtoAlert(driver);
		seleniumLib.acceptAlert(alert);

		imgEle=contact.getLoadingImg();
		seleniumLib.waitForElementInvisibility(imgEle);

		//verifying the deleted data
		contact.enterSearchtext(lastName);

		//selecting the category
		searchContext=contact.getCategoryDropdwonEle();
		seleniumLib.initSelect(searchContext);
		seleniumLib.selectByValue("lastname");

		//searching now
		contact.getSearchNowEle().click();
		
		//deleted verification text
		String contactInfo =contact.getVerificationDeletText().getText();
		System.out.println(contactInfo);

		//Singing out from the application
		home.logout();

		//close the workbook and browser
		excelLib.closeWorkBook();
		seleniumLib.closeBrwoser();
	}
}
