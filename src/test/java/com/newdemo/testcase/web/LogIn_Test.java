package test.java.com.newdemo.testcase.web;

import java.util.concurrent.ConcurrentHashMap;
import main.java.com.newdemo.framework.base.BaseSetupClass;
import main.java.com.newdemo.framework.base.ReusableFunctions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class LogIn_Test extends BaseSetupClass{

	public ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> getDataScript= null;
	public WebDriver driver= null;
	public ReusableFunctions functions= null;

	@BeforeMethod(alwaysRun= true)
	public void init() throws Exception {

		getDataScript= getTestData();
		driver= getDriver();
		functions= new ReusableFunctions(driver);
	}


	@Test(description = "Login Test Case")
	public void signInTest() throws Exception {
				
		String testCaseName= new Throwable().getStackTrace()[0].getMethodName().toString();
		ConcurrentHashMap<String, Object> getScriptData= getDataScript.get(testCaseName);

		App().WebLogin().launchURL();
		Thread.sleep(4000);
		App().WebLogin().clickusername();
		App().WebLogin().typeUser(getScriptData.get("UserName").toString());
		App().WebLogin().clickpassword();
		App().WebLogin().typePassword(getScriptData.get("Password").toString());
		App().WebLogin().clickSignin();
		App().WebLogin().waitForHomePageHeader();
		System.out.println("Hey..! Login Successful....!");
     }
	
		
}