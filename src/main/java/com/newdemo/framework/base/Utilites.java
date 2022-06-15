package main.java.com.newdemo.framework.base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    

	public static List<Map<String, String>> getResultSet(Connection con, String selectStmt, String... columnName)
			throws SQLException {
		Statement stmt;
		ResultSet rs;
		Map<String, String> rsvalueMap = new HashMap<String, String>();
		List<Map<String, String>> results = new LinkedList<Map<String, String>>();
		try {
			con.setAutoCommit(false);
			// Create the Statement
			stmt = con.createStatement();

			// Execute a query and generate a ResultSet instance
			rs = stmt.executeQuery(selectStmt);
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();

			while (rs.next()) {
				int counter = 0;
				while (counter < colCount) {
					rsvalueMap.put(columnName[counter], rs.getString(counter + 1));
					counter += 1;
				}
				results.add(rsvalueMap);
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.err.println("SQLException information");
			while (ex != null) {
				System.err.println("Error msg: " + ex.getMessage());
				System.err.println("SQLSTATE: " + ex.getSQLState());
				System.err.println("Error code: " + ex.getErrorCode());
				ex.printStackTrace();
				ex = ex.getNextException();
			}
		}
		return results;
	}
	
	
	public static Map<String, String> getResultSetWithMultiColumnOneRow(Connection con, String selectStmt, String... columnName)
			throws SQLException {
		Statement stmt;
		ResultSet rs;
		Map<String, String> rsvalueMap = new HashMap<String, String>();
		try {
			con.setAutoCommit(false);
			// Create the Statement
			stmt = con.createStatement();

			// Execute a query and generate a ResultSet instance
			rs = stmt.executeQuery(selectStmt);
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();

			while (rs.next()) {
				int counter = 0;
				while (counter < colCount) {
					rsvalueMap.put(columnName[counter], rs.getString(counter + 1));
					counter += 1;
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.err.println("SQLException information");
			while (ex != null) {
				System.err.println("Error msg: " + ex.getMessage());
				System.err.println("SQLSTATE: " + ex.getSQLState());
				System.err.println("Error code: " + ex.getErrorCode());
				ex.printStackTrace();
				ex = ex.getNextException();
			}
		}
		return rsvalueMap;
	}


	public static List<String> getResultSetWithOneColumn(Connection con, String selectStmt) throws SQLException {
		Statement stmt;
		ResultSet rs;
		List<String> results = new LinkedList<String>();
		try {
			con.setAutoCommit(false);
			// Create the Statement
			stmt = con.createStatement();
			// Execute a query and generate a ResultSet instance
			rs = stmt.executeQuery(selectStmt);
			while (rs.next()) {
				results.add(rs.getString(1).trim());
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.err.println("SQLException information");
			while (ex != null) {
				System.err.println("Error msg: " + ex.getMessage());
				System.err.println("SQLSTATE: " + ex.getSQLState());
				System.err.println("Error code: " + ex.getErrorCode());
				ex.printStackTrace();
				ex = ex.getNextException();
			}
		}
		return results;
	}
	
	
	public static String getResultSetWithOneColumnOneRow(Connection con, String selectStmt) throws SQLException {
		Statement stmt;
		ResultSet rs;
		String result = null;
		try {
			con.setAutoCommit(false);
			// Create the Statement
			stmt = con.createStatement();
			// Execute a query and generate a ResultSet instance
			rs = stmt.executeQuery(selectStmt);
			rs.next();
			result = rs.getString(1);
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.err.println("SQLException information");
			while (ex != null) {
				System.err.println("Error msg: " + ex.getMessage());
				System.err.println("SQLSTATE: " + ex.getSQLState());
				System.err.println("Error code: " + ex.getErrorCode());
				ex.printStackTrace();
				ex = ex.getNextException();
			}
		}
		return result;
	}

	
	public static int getNumberOfRows(Connection con, String selectStmt) throws SQLException {
		Statement stmt;
		ResultSet rs;
		int counter = 0;
		try {
			con.setAutoCommit(false);
			System.out.println("**** Created a JDBC connection to the data source");

			// Create the Statement
			stmt = con.createStatement();

			// Execute a query and generate a ResultSet instance
			rs = stmt.executeQuery(selectStmt);

			while (rs.next()) {
				counter = counter + 1;
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.err.println("SQLException information");
			while (ex != null) {
				System.err.println("Error msg: " + ex.getMessage());
				System.err.println("SQLSTATE: " + ex.getSQLState());
				System.err.println("Error code: " + ex.getErrorCode());
				ex.printStackTrace();
				ex = ex.getNextException();
			}
		}
		return counter;
	}
	

	public static void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    
    
	
}
