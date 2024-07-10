package genericUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import com.github.javafaker.DateAndTime;
import com.github.javafaker.Faker;
import com.github.javafaker.PhoneNumber;

/**
 * this class contains java specific libraries
 * @author Lakshmi
 *
 */
public class JavaUtility 
{
	Faker faker=new Faker();
	
	/**
	 * this method is used to generate integer random numbers 
	 * with in the boundary of 0 to 1000
	 * @return int data
	 */
	public int getRandNum()
	{
		Random ran=new Random();
		int randomNum=ran.nextInt(10000);
		return randomNum;
	}
	
	/** 
	 * it is used to generate Random mobile number
	 * @return
	 */
	public String getRandMobNum()
	{
		return RandomStringUtils.randomNumeric(9, 10);
	}

	/**
	 * it is used to generate Random AlphaNumeric values
	 * @return
	 */
	public String getRandAplhaNumValue()
	{
		return RandomStringUtils.randomAlphanumeric(10);
	}
	
	/**
	 * this method is used to get date and time of the current system
	 * @return string data
	 */
	public String getSystemDate()
	{
		Date date=new Date();
		return date.toString();
	}

	/**
	 * it is used to get current system date and time with the format as YYYY-MM-DD
	 * @return date
	 */
	public String getSystemDate_YYYY_MM_DD()
	{
		Date date=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}
	
	/**
	 * this method is used to get date and time with user specified format
	 * @param format
	 * @return
	 */
	public String getSystemDateTime(String format)
	{
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	
	/**
	 * this is used to get required date based specified days 
	 * @param days
	 * @return
	 */
	public String getDateBasedOnTAT(int tatDay)
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal=sdf.getCalendar();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, tatDay);
		String reqDate=sdf.format(cal.getTime());
		return reqDate;
	}
	
	/**
	 * this method is used to get system current time in milleseconds
	 * @return
	 */
	public long getCurrentTimeInMilleseconds()
	{
		return System.currentTimeMillis();
	}
	
	/**
	 * used to generate random first name
	 * @return
	 */
	public String getRandFname()
	{
		return faker.name().firstName();
	}
	
	/**
	 * used to generate random last name
	 * @return
	 */
	public String getRandLname()
	{
		return faker.name().lastName();
	}
	
	/**
	 * used to generate random mobile numbers
	 * @return
	 */
	public PhoneNumber getMobileNum()
	{
		return faker.phoneNumber();
	}
	
	/**
	 * used to generate random comany names
	 * @return
	 */
	public String getRandOrgName()
	{
		return faker.company().name();
	}
	
	/**
	 * used to generate random company url
	 */
	public String getRandWebsiteName()
	{
		String url=faker.company().url();
		return url="http://" + url;
	}
	
	public DateAndTime getRandDate()
	{
		return faker.date();
	}
}
