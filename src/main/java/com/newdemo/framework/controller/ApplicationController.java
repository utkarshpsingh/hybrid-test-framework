package com.newdemo.framework.controller;


import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import com.newdemo.framework.base.BaseSetupClass;
import com.newdemo.framework.data.WebData;



public class ApplicationController
{

	public  WebDriver driver = BaseSetupClass.getDriver();

	//====================CONTROLLER OBJECTS=======================

	public HashMap <String, Object> getData;
	public String strParametersNValues = "";
	public String strMainParametersNValues = "";
	public LoginController login = null;
	public WebData loginData = null;
	public TestScenarioDataController testScenarioData =null;
	
	public ApplicationController(WebDriver driver)
	{
		this.driver = driver;
	
	}
	

	public LoginController Login() throws Exception
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
			testScenarioData= new TestScenarioDataController(driver);
		}
		
		return testScenarioData;
		
		
	}
	
	
	
}