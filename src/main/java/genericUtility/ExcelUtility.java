package genericUtility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * This class contains Excel utilities
 * @author Lakshmi
 */
public class ExcelUtility 
{
	private Workbook work;
	private DataFormatter df;
	
	/**
	 * this method is used conncect excel sheet
	 * @param sheetName
	 * @param filePath
	 * @return 
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	public void initExcelFile(String filePath) throws EncryptedDocumentException, IOException
	{
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+filePath);
		df=new DataFormatter();
		work=WorkbookFactory.create(fis);
		fis.close();
	}
	
	/**
	 * it is used to close the workbook
	 * @param work
	 * @throws IOException
	 */
	public void closeWorkBook() throws IOException
	{
		work.close();
	}
	
	/**
	 * this method is used get specified cell data
	 * @param rowNum
	 * @param cellNum
	 * @return
	 */
	public String getCellData(String sheetName, int rowNum, int cellNum)
	{
		Sheet sheet = work.getSheet(sheetName);
		Cell celldata=sheet.getRow(rowNum).getCell(cellNum);
		return df.formatCellValue(celldata);
	}

	/**
	 * this is used get length of Row
	 */
	public int getRowCount(String sheetName)
	{
		Sheet sheet = work.getSheet(sheetName);
		return sheet.getLastRowNum();
	}

	/**
	 * this method is used to get Fisrt row num
	 * @return
	 */
	public int getFirstRowNum(String sheetName)
	{
		Sheet sheet = work.getSheet(sheetName);
		return sheet.getFirstRowNum();
	}

	/**
	 * this is used to get last row num
	 * @return
	 */
	public int getLastRowNum(String sheetName)
	{
		Sheet sheet = work.getSheet(sheetName);
		return sheet.getLastRowNum();
	}

	/**
	 * this is used to check formula is present is any cell of the excel sheet
	 * @return
	 */
	public boolean checkFormulaIsDisplay(String sheetName)
	{
		Sheet sheet = work.getSheet(sheetName);
		return sheet.isDisplayFormulas();
	}

	/**
	 * this is used to get value of specified column
	 * @param tc_ID
	 * @param headerName
	 * @return
	 */
	public String getSpecifiedColumnData(String sheetName, String tc_ID, String headerName)
	{
		Sheet sheet = work.getSheet(sheetName);
		String value="";
		for(int i=0; i<=sheet.getLastRowNum();i++)
		{
			String id=df.formatCellValue(sheet.getRow(i).getCell(0));
			if(id.equalsIgnoreCase(tc_ID))
			{
				for(int j=0; j<sheet.getRow(i-1).getLastCellNum(); j++)
				{
					String header=df.formatCellValue(sheet.getRow(i-1).getCell(j));
					if(header.equalsIgnoreCase(headerName))
					{
						value=df.formatCellValue(sheet.getRow(i).getCell(j));
					}
				}
				break;
			}
		}
		return value;
	}

	/**
	 * this is used to get all header and values of specified row data
	 * @param tc_ID
	 * @return
	 */
	public Map<String,String> getAllHeaderAndValues_of_SpecifiedData(String sheetName,String tc_ID)
	{
		Sheet sheet = work.getSheet(sheetName);
		Map<String,String> map=new LinkedHashMap<String,String>();

		for(int i=0; i<=sheet.getLastRowNum(); i++)
		{
			String id=df.formatCellValue(sheet.getRow(i).getCell(0));
			if(id.equalsIgnoreCase(tc_ID))
			{
				for(int j=0; j<sheet.getRow(i-1).getLastCellNum(); j++)
				{
					String header=df.formatCellValue(sheet.getRow(i-1).getCell(j));
					String value=df.formatCellValue(sheet.getRow(i).getCell(j));
					map.put(header, value);
				}
				break;
			}
		}
		return map;
	}

	/**
	 * this method to used to get all data present in excel
	 * @return
	 */
	public List<Map<String, String>> getAllData_In_Excel(String sheetName)
	{
		Sheet sheet = work.getSheet(sheetName);
		List<Map<String, String>> listMap=new LinkedList<Map<String,String>>();
		for(int i=0; i<=sheet.getLastRowNum(); i++)
		{
			String tc_ID=df.formatCellValue(sheet.getRow(i).getCell(0));
			Map<String,String> map=new LinkedHashMap<String,String>();
			if(tc_ID.startsWith("tc"))
			{
				for(int j=0; j<sheet.getRow(i-1).getLastCellNum();j++)
				{
					String header=df.formatCellValue(sheet.getRow(i-1).getCell(0));
					String value=df.formatCellValue(sheet.getRow(i).getCell(j));
					map.put(header, value);
				}
			}
			listMap.add(map);
		}
		return listMap;
	}

	/**
	 * this method is used to write data into excel
	 * based on index
	 * @param filePath
	 * @param rowIndex
	 * @param columnIndex
	 * @param cellValue
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void writeDataIntoExcel_basedOnIndex(String sheetName, String filePath,int rowIndex, int columnIndex, String cellValue) throws FileNotFoundException, IOException
	{
		Sheet sheet = work.getSheet(sheetName);
		Row row=sheet.getRow(rowIndex);
		if(row==null)
			row=sheet.createRow(rowIndex);

		Cell cell=row.getCell(columnIndex);
		cell.setCellValue(cellValue);

		work.write(new FileOutputStream(filePath));
	}

	/**
	 * this method is used to write the data into excel
	 * based on Key
	 * @param filePath
	 * @param tc_ID
	 * @param keyValue
	 * @param cellValue
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void writeDataIntoExcel_basedOnKeyValue(String sheetName,String filePath, String tc_ID, String keyValue, String cellValue) throws FileNotFoundException, IOException
	{
		Sheet sheet = work.getSheet(sheetName);
		for(int i=0; i<=sheet.getLastRowNum(); i++)
		{
			String id=df.formatCellValue(sheet.getRow(i).getCell(0));
			if(id.equalsIgnoreCase(tc_ID))
			{
				for(int j=0; j<sheet.getRow(i-1).getLastCellNum(); j++)
				{
					String key=df.formatCellValue(sheet.getRow(i-1).getCell(j));
					if(key.equalsIgnoreCase(keyValue))
					{
						sheet.getRow(i).createCell(j).setCellValue(cellValue);
						break;
					}
				}
				break;
			}
		}
		work.write(new FileOutputStream(filePath));
	}

	/**
	 * this method is used to write the data into excel
	 * based on last cell num
	 * @param filePath
	 * @param tc_ID
	 * @param keyValue
	 * @param columnValue
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void writeDataIntoExcel_atLastColumn(String sheetName, String filePath, String tc_ID, String keyValue, String columnValue) throws FileNotFoundException, IOException
	{
		Sheet sheet = work.getSheet(sheetName);
		for(int i=0; i<=sheet.getLastRowNum(); i++)
		{
			String id=df.formatCellValue(sheet.getRow(i).getCell(0));
			if(id.equalsIgnoreCase(tc_ID))
			{
				short lastCell=sheet.getRow(i).getLastCellNum();
				for(int j=0; j<sheet.getRow(i-1).getLastCellNum(); j++)
				{
					sheet.getRow(i-1).createCell(lastCell).setCellValue(keyValue);
					sheet.getRow(i).createCell(lastCell).setCellValue(columnValue);
					break;
				}
				break;
			}
		}
		work.write(new FileOutputStream(filePath));
	}
}
