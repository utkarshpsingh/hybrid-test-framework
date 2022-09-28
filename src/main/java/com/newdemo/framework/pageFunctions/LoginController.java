package main.java.com.newdemo.framework.pageFunctions;

import main.java.com.newdemo.framework.base.ReusableFunctions;
import main.java.com.newdemo.framework.data.WebData;
import main.java.com.newdemo.framework.pageObjects.HomePage;
import main.java.com.newdemo.framework.pageObjects.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class LoginController extends ReusableFunctions
{
	LoginPage login = null;
	HomePage homePage = null;
	WebData loginData = null;

	public LoginController(WebDriver driver) throws Exception 
	{
		super(driver); //  driver instance of ReusableFunctions class that all the page objects inherit from
			login = PageFactory.initElements(driver, LoginPage.class);
			homePage = PageFactory.initElements(driver, HomePage.class);
			loginData= new WebData();
	}
	
	public void launchURL() throws Exception
	{
		launchURL(loginData.strDTURL);
	}

	public void clickLoginButton() throws Exception
	{
		clickObject(login.btnSubmit, "Login Button");
	}

	public void typeUser(String email) throws Exception
	{
		typeValue(login.userName, "userName", email);
	}

	public void typePassword(String password) throws Exception
	{
		typeValue(login.enterPassword, "password", password);
	}

	public void waitForUserName(int intWaitTime) throws Exception
	{
		waitForElement(waitCondition.tobePresent, login.userName);
	}

	public void waitForHomePageHeader() throws Exception
	{
		String headerText= getText(homePage.projectHeader,"Header Project","");
		Assert.assertEquals(headerText,"PRODUCTS");
	}
	public void clickSignin() throws Exception
	{
		clickObject(login.btnSubmit, "SignIn");
	}

	public void clickusername() throws Exception
	{
		clickObject(login.userName, "username clicked");
	}

	public void clickpassword() throws Exception
	{
		clickObject(login.enterPassword, "password clicked");

	}

	public void loginToApplication(String userName, String password) throws Exception {

		launchURL();
		Thread.sleep(4000);
			clickusername();
				typeUser(userName);
		clickpassword();
			typePassword(password);
				clickSignin();
		waitForHomePageHeader();

		System.out.println("Hey..! Login Successful....!");

	}
	
}
