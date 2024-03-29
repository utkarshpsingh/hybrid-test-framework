package main.java.com.newdemo.framework.controller;


import java.util.HashMap;

import main.java.com.newdemo.framework.base.BaseSetupClass;
import main.java.com.newdemo.framework.data.WebData;
import main.java.com.newdemo.framework.pageFunctions.LoginController;
import org.openqa.selenium.WebDriver;

public class ApplicationController{

	public  WebDriver driver = BaseSetupClass.getDriver();

	//====================CONTROLLER OBJECTS=======================

	public HashMap <String, Object> getData;
	public LoginController login = null;
	public WebData loginData = null;
	public TestScenarioDataController testScenarioData =null;
	
	public ApplicationController(WebDriver driver)
	{
		this.driver = driver;
	
	}

	public LoginController WebLogin() throws Exception
	{
		if(login == null)
		{
			login = new LoginController(driver);
		}
		return login;
	}
	

	public TestScenarioDataController TestScenarioData() throws Exception {
		
		if (testScenarioData==null) 
		{
			testScenarioData= new TestScenarioDataController();
		}
		return testScenarioData;

	}
	
	
	
}