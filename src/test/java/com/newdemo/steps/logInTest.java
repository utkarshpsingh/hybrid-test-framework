package com.newdemo.steps;

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
		getData= Trepp().TestScenarioData().getDataForSheetTestData(testCaseName);
		
		
		Trepp().Login().launchURL();
		Thread.sleep(10000);
		Trepp().Login().clickSignInLink();
		Thread.sleep(10000);
		Trepp().Login().waitForUserName(10);
		Trepp().Login().clickusername();
		Trepp().Login().typeEmail(getData.get("UserName").toString());
		Trepp().Login().clickpassword();
		Trepp().Login().typePassword(getData.get("Password").toString());
		Trepp().Login().clickSignin();
		
     }
}