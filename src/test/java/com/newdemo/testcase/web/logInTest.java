package test.java.com.newdemo.testcase.web;

import java.util.concurrent.ConcurrentHashMap;
import main.java.com.newdemo.framework.base.BaseSetupClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Test
public class logInTest extends BaseSetupClass
{
	
	@Test(description = "Login Test Case")
	public void signInTest() throws Exception {
				
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