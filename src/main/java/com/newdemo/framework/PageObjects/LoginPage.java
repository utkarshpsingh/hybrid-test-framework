package com.newdemo.framework.PageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
	@FindBy(css = "button[ng-click='login()']") public WebElement LoginButton;
	@FindBy(xpath=".//*[@name='email']") public WebElement userName;
	@FindBy(name="password") public WebElement password;
	@FindBy(xpath=".//button[@class='auth0-lock-submit']") public WebElement Login;
	@FindBy(xpath=".//button[@title='test']") public WebElement testEnv;
	@FindBy(xpath=".//a[contains(text(),'Analytics')]") public WebElement Analytics;
	@FindBy(xpath=".//a[contains(text(),'Derivative')]") public WebElement Derivative;
	@FindBy(xpath="..//a[contains(text(),'Loan')]") public WebElement Loan;
	 
}
