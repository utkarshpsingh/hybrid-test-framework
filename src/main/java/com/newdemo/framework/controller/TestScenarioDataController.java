package main.java.com.newdemo.framework.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import main.java.com.newdemo.framework.data.TestScenarioData;
import main.java.com.newdemo.framework.pageObjects.LoginPage;


public class TestScenarioDataController {
	
	LoginPage login = null;
	TestScenarioData testScenarioData = null;

	public TestScenarioDataController() throws Exception {
		testScenarioData = new TestScenarioData();
	}

	
	 public synchronized ConcurrentHashMap<String, Object> getDataForSheetTestData(String testCaseName) throws IOException {
		
		 String filePath =System.getProperty("user.dir")+"/Input/TestData.xlsx";
		 	String sheetName= "TestData";
	 	 
		 	ConcurrentHashMap<String,Object> data =testScenarioData.loadTestData(testCaseName, filePath, sheetName, "");
		
	 	return data; 
	 }

	public synchronized ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> getDataForSheetTestData() throws IOException {

		String filePath =System.getProperty("user.dir")+"/Input/TestData.xlsx";
		String sheetName= "TestData";

		ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> data =testScenarioData.loadTestData(filePath, sheetName, "");

		return data;
	}


}
