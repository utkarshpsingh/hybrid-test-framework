package main.java.com.newdemo.framework.pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
	
	
	@FindBy(xpath = "//a[contains(text(),'Sign in')]") public WebElement btnSignIn;
	@FindBy(xpath = "//input[@id='email']") public WebElement enterEmail;
	@FindBy(xpath = "//input[@id='passwd']") public WebElement enterPassword;
	@FindBy(xpath = "//button[@id='SubmitLogin']") public WebElement btnSubmit;
		
	 
}
