package com.vTiger.crm.testNgTest;

import java.io.IOException;
import java.util.Map;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import baseClassTestng.BaseClassTestNg;
import genericUtility.IConstants;
import objectRepo.ContactInfoPage;
import objectRepo.ContactsPage;
import objectRepo.CreateContactPage;
import objectRepo.HomePage;
import objectUtlity.TransferObject;

public class TC001_CreateContactsTest extends BaseClassTestNg
{
	@Test(groups="Smoke")
	public void createContact() throws IOException
	{
//		//creating a Test 
		ExtentTest test=TransferObject.getTest();
		
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
		String homePageVeriTxt=map1.get("HomePage_Txt");
		String contactCreatePageTXT=map1.get("Contact_CreatePageText");
		String contactPageVeriTxt=map1.get("ContactPage_Txt");
		String contact_InformationPageText=map1.get("Contact_InformationPageText");
		String invalidPageMsg=map1.get("Invalid_Page_Message");
		String invalidDataMsg=map1.get("Invalid_Data_Message");
		
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
			test.log(Status.INFO,"FirstName is: " + firstName);
		else
			throw new RuntimeException(invalidDataMsg);

		String actLastName=verification.getLnameEle().getText();
		if(actLastName.equalsIgnoreCase(lastName))
			test.log(Status.INFO,"LastName is: " + lastName);
		else
			throw new RuntimeException(invalidDataMsg);

		String actMobileNum=verification.getMobileEle().getText();
		if(actMobileNum.equalsIgnoreCase(mobileNumber))
			test.log(Status.INFO,"ContactNum is: " + mobileNumber);
		else
			throw new RuntimeException(invalidDataMsg);		

		//closing workbook
		excelLib.closeWorkBook();
	}
}

