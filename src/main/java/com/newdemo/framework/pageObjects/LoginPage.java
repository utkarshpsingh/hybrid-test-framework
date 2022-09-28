package main.java.com.newdemo.framework.pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class LoginPage {


	@FindBy(xpath = "//input[@id='user-name']") public WebElement userName;
	@FindBy(xpath = "//input[@id='password']") public WebElement enterPassword;
	@FindBy(xpath = "//input[@id='login-button']") public WebElement btnSubmit;
		
	 
}
