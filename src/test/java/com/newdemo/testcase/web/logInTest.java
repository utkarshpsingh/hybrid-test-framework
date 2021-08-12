package com.newdemo.testcase.web;

import java.util.concurrent.ConcurrentHashMap;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.newdemo.framework.base.BaseSetupClass;

@Test
public class logInTest extends BaseSetupClass
{
	@Parameters("ParameterNValue")
	@Test()
	public void signInTest(String ParameterNValue) throws Exception
	
	{
		
		this.strDTParametersNValues = ParameterNValue;
		String testCaseName= this.getClass().getSimpleName();
		ConcurrentHashMap<String, Object> getData;
		getData= App().TestScenarioData().getDataForSheetTestData(testCaseName);
		
		
		App().Login().launchURL();
		Thread.sleep(10000);
		App().Login().clickSignInLink();
		Thread.sleep(10000);
		App().Login().waitForUserName(10);
		App().Login().clickusername();
		App().Login().typeEmail(getData.get("UserName").toString());
		App().Login().clickpassword();
		App().Login().typePassword(getData.get("Password").toString());
		App().Login().clickSignin();
		
     }
}