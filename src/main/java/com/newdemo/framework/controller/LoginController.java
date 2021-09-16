package com.newdemo.framework.controller;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import com.newdemo.framework.base.ReuseableFunctions;
import com.newdemo.framework.data.LoginData;
import com.newdemo.framework.pageObjects.LoginPage;

public class LoginController extends ReuseableFunctions
{
	LoginPage login = null;
	LoginData loginData = null;
	
	
	public LoginController(WebDriver driver) throws Exception 
	{
		super(driver); //  driver instance of ReuseableFunctions class that all the page objects inherit from
			login = PageFactory.initElements(driver, LoginPage.class);
	}
	
	public void launchURL() throws Exception
	{
		launchURL(loginData.strDTURL);
	}
	
	public void clickSignInLink() throws Exception
	{
		clickObject(login.btnSignIn, "Sign In Link");
	}
	
	public void clickLoginButton() throws Exception
	{
		clickObject(login.btnSubmit, "Login Button");
	}
	
	public void typeEmail(String email) throws Exception
	{
		typeValue(login.enterEmail, "userName", email);
	}
	
	public void typePassword(String password) throws Exception
	{
		typeValue(login.enterPassword, "password", password);
	}
	
	public void waitForUserName(int intWaitTime) throws Exception
	{
		waitForElement(waitCondition.toBeClickable, login.enterEmail);
	}
	
	public void clickSignin() throws Exception
	{
		clickObject(login.btnSubmit, "SignIn");
	}
	
	public void clickusername() throws Exception
	{
		clickObject(login.enterEmail, "username clicked");
	}

	public void clickpassword() throws Exception
	{
		clickObject(login.enterPassword, "password clicked");
	
	}
	
		
	
}
