package com.newdemo.framework.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.openqa.selenium.WebDriver;

import com.newdemo.framework.base.ReuseableFunctions;
import com.newdemo.framework.data.TestScenarioData;
import com.newdemo.framework.pageObjects.LoginPage;


public class TestScenarioDataController extends ReuseableFunctions {
	
	LoginPage login = null;
	TestScenarioData testScenarioData = null;
	
	
	public TestScenarioDataController(WebDriver driver) throws Exception {
		super(driver);
		testScenarioData = new TestScenarioData();
		
	}	
	
	
	 public synchronized ConcurrentHashMap<String, Object> getDataForSheetTestData(String testCaseName) throws IOException {
		
		 String filePath =System.getProperty("user.dir")+"/Input/TestData.xlsx";
		 	String sheetName= "TestData";
	 	 
		 	ConcurrentHashMap<String,Object> data =testScenarioData.loadTestData(testCaseName, filePath, sheetName, "");
		
	 	return data; 
	 }


}
