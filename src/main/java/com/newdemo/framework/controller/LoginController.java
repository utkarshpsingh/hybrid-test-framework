package com.newdemo.framework.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.newdemo.framework.base.Utilites;
import com.newdemo.framework.base.BaseSetupClass;
import com.newdemo.framework.base.ReuseableFunctions;
import com.newdemo.framework.PageObjects.LoginPage;
import com.newdemo.framework.data.LoginData;

public class LoginController extends ReuseableFunctions
{
	LoginPage login = null;
	LoginData loginData = null;
	String sheet1 = System.getProperty("user.dir") + "/Results/Test1.csv";
	
	String sheet2 = System.getProperty("user.dir") + "/Results/Test2.csv";
	
	File file2 = new File(sheet2);
	
	
	
	public LoginController(WebDriver driver) throws Exception 
	{
		super(driver); //  driver instance of ReuseableFunctions class that all the page objects inherit from
		
		login = PageFactory.initElements(driver, LoginPage.class);
		
	}
	
	public void launchURL() throws Exception
	{
		launchURL(loginData.strDTURL);
		
		//runpython(file1);
	}
	
	public void clickLoginButton() throws Exception
	{
		clickObject(login.LoginButton, "Login Button");
	}
	
	public void typeEmail() throws Exception
	{
		typeValue(login.userName, "userName", loginData.strDTUserName);
	}
	
	public void typePassword() throws Exception
	{
		typeValue(login.password, "password", loginData.strDTPassword);
	}
	
	public void waitForUserName(int intWaitTime) throws Exception
	{
		waitForElement(waitCondition.toBeClickable, login.userName);
	}
	
	public void clickSignin() throws Exception
	{
		clickObject(login.Login, "SignIn");
	}
	public void clickusername() throws Exception
	{
		clickObject(login.userName, "username clicked");
	}

	public void clickpassword() throws Exception
	{
		clickObject(login.password, "password clicked");
	
	}
	
	public void waitFortestEnv(int intWaitTime) throws Exception
	{
		waitForElement(login.testEnv);
	}
	
	public void waitForAnalytics(int intWaitTime) throws Exception
	{
		waitForElement(login.Analytics);
	}
	
	public void clickAnalytics() throws Exception
	{
		clickObject(login.Analytics, "Analytics clicked");
	
	}
	
	public void clicktestEnv() throws Exception
	{
		clickObject(login.testEnv, "testEnv clicked");
	
	}
	
	
}
