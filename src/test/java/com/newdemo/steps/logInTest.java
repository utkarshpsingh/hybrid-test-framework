package com.newdemo.steps;

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
	
		Trepp().Login().launchURL();
		Trepp().Login().clickLoginButton();
		Trepp().Login().waitForUserName(15);
		Trepp().Login().clickusername();
		Trepp().Login().typeEmail();
		Trepp().Login().clickpassword();
		Trepp().Login().typePassword();
		Trepp().Login().clickSignin();
		
     }
}