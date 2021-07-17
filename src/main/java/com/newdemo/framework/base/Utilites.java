package com.newdemo.framework.base;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;



public class Utilites
{

	
	
	public HashMap<String, String> SplitNStoreParamsInHashMap(String strParameters)
	{
		HashMap<String, String> objHashMap = new HashMap<String, String>();
		if(strParameters != "" && strParameters != null)
		{
			String[] arrKeysNValues = strParameters.split("--");

			for(int intArrElt = 0; intArrElt < arrKeysNValues.length; intArrElt++)
			{
				if(arrKeysNValues[intArrElt] != "" && arrKeysNValues[intArrElt] != null)
				{
					String[]  arrCurrKeyNValue = arrKeysNValues[intArrElt].split("=>");
					objHashMap.put(arrCurrKeyNValue[0], arrCurrKeyNValue[1]);
				}
			}
		}
		return objHashMap;
	}

	     public String readTextFileAndGetAsString(String strFilePath) throws IOException
	     {
	            try
	            {
	                   BufferedReader bufferedReader = new BufferedReader(new FileReader(strFilePath));
	                  
	                   String txtFileLines = "";
	                   String strCurrentLine = "";
	                   while((strCurrentLine = bufferedReader.readLine()) != null)
	                   {
	                         txtFileLines = txtFileLines + strCurrentLine;
	                   }
	                  
	                   System.out.println(txtFileLines);
	                   return txtFileLines;
	            }
	            catch(Exception e)
	            {
	                   System.out.println("Exception while reading the file " + strFilePath + "\n" + e.toString());
	                   return null;
	            }
	     }

	public int FindColumnNoInExcel(HSSFSheet objSH, String strColumnName, int intRowNum) throws Exception
	{
		intRowNum = intRowNum - 1;
		HSSFRow objRow = objSH.getRow(intRowNum);

		int intLastCellNum;
		String strAvailCellValue;

		try
		{
			intLastCellNum = objRow.getLastCellNum();
			for(int intCell = 0; intCell < intLastCellNum; intCell++)
			{
				strAvailCellValue = objSH.getRow(intRowNum).getCell(intCell).getStringCellValue();
				if(strAvailCellValue.equalsIgnoreCase(strColumnName))
				{
					return intCell;
				}
			}
		}
		catch(Exception e)
		{
			return -1;
		}
		return -1;
	}



	
	public String GetCellValueForRowNum(HSSFSheet objSH, String strColumnName, int intRowNum) throws Exception
	{
		
		
		intRowNum = intRowNum - 1;
		int intXLColumn;
		intXLColumn = this.FindColumnNoInExcel(objSH, strColumnName, 1);

		String strAvailCellValue = null;
		Cell objXLCell;
		try
		{
			objXLCell = objSH.getRow(intRowNum).getCell(intXLColumn);
			objXLCell.setCellType(CellType.STRING);// to convert cell type to string
			strAvailCellValue = objXLCell.getStringCellValue();
			return strAvailCellValue.trim();
		}
		catch(Exception e)
		{
			//System.out.println("Exception " + e);
			return "";
		}
	}

	
	public Boolean SetCellValueForRowNum(HSSFSheet objSH, String strColumnName, int intRowNum, String strValue) throws Exception
	{
		//intRowNum = intRowNum - 1;
		int intRowCount, intColumnCount, intColumnNum;
		intRowCount = objSH.getLastRowNum();

		HSSFRow objRow = null;

		intColumnNum = this.FindColumnNoInExcel(objSH, strColumnName, 1);

		if(intRowCount >= intRowNum)
		{
			if(objSH.getRow(intRowNum) != null)
			{
				objRow = objSH.getRow(intRowNum);
			}
			else
			{
				objRow = objSH.createRow(intRowNum);
			}

			if(objRow.getCell(intColumnNum) != null)
			{
				objRow.getCell(intColumnNum).setCellValue(strValue);
			}
			else
			{
				objRow.createCell(intColumnNum).setCellValue(strValue);
			}
			return true;
		}
		else
		{
			if(objSH.getRow(intRowNum) != null)
			{
				objRow = objSH.getRow(intRowNum);
			}
			else
			{
				objRow = objSH.createRow(intRowNum);
			}
			objRow.createCell(intColumnNum).setCellValue(strValue);
			return true;
		}
	}


	public String GetCellValueInExcel(HSSFSheet objSH, int intRow, int intColumn)
	{
		String strCellValue;
		try
		{
			strCellValue = objSH.getRow(intRow).getCell(intColumn).getStringCellValue().trim();
		}
		catch(Exception e)
		{
			strCellValue = "";
		}
		return strCellValue;
	}


	
	public String GetExceptionNDisplay(Exception objException, boolean blnIsDisplay) throws Exception
	{
		String strException = objException.getMessage();
		if(strException != null)
		{
			String[] arrException = strException.split("\n");
			if(blnIsDisplay == true)
			{
				System.out.println("Exception occurred " + arrException[0]);
			}
			return "<font color='blue'>" + arrException[0] + "</font>";
		}
		else
		{
			return "<font color='blue'>No specific error message thrown from driver for the current step. Check error message in previous steps</font>";
		}
	}


   

   

    //==========================CONNECT TO DB=======================================================
    public void ConnectDB() throws Exception
    {

	     Properties objProperties = new Properties();
	     objProperties.load(new FileReader(System.getProperty("user.dir") + "/Config/CONFIG.properties"));
         String url1 =objProperties.getProperty("DBURL");
         String db = "";
		 String username = objProperties.getProperty("Username");
		 String password = objProperties.getProperty("Password");
		 String Query = objProperties.getProperty("Query");

		 Class.forName(db).newInstance();
         Connection con = DriverManager.getConnection(url1,username, password);
         Statement stmt = (Statement) con.createStatement();
         ResultSet raw = (ResultSet) stmt.executeQuery(Query);
         String raw1 = raw.toString();
         String [] result=raw1.split("@");
         System.out.println(result[1]);

    }



	public static void main(String[] args) throws Exception
	{

	}

}
