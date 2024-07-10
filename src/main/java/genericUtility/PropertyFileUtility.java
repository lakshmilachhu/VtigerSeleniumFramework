package genericUtility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * this class contains of property file utilities
 * @author Lakshmi
 *
 */
public class PropertyFileUtility 
{
	private Properties prop;
	/**
	 * this method is used to connect the property file
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public void initPropertyFile(String filePath) throws IOException
	{
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+ filePath);
		prop=new Properties();
		prop.load(fis);
		fis.close();
	}
	
	/**
	 * this method is used to get key data from properties
	 * @param keyData
	 * @return
	 */
	public String getData(String keyData)
	{
		return prop.getProperty(keyData.trim(),"key is not present").trim();
	}
		
}
