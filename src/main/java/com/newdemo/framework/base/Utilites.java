package com.newdemo.framework.base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;
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


   

    //==========================================CONNECT TO DataBase=============================================
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
