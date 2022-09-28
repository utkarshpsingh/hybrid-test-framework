package test.java.com.newdemo.testcase.web;

import main.java.com.newdemo.framework.base.BaseSetupClass;
import main.java.com.newdemo.framework.base.ReuseableFunctions;
import main.java.com.newdemo.framework.pageObjects.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.ConcurrentHashMap;

@Test
public class CheckOutCart_Test extends BaseSetupClass
{

	public ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> getData= null;
	public WebDriver driver= null;
	public ReuseableFunctions functions= null;
	HomePage homePage = null;

	@BeforeMethod(alwaysRun= true)
	public void init() throws Exception {
		getData= getTestData();
		driver= getDriver();
		functions= new ReuseableFunctions(driver);
		homePage = PageFactory.initElements(driver, HomePage.class);

	}

	@Test(description = "Adding One Item to Cart")
	public void checkOutForOneItem() throws Exception {
				
		String testCaseName= new Throwable().getStackTrace()[0].getMethodName().toString();
		ConcurrentHashMap<String, Object> getScriptData= getData.get(testCaseName);

		App().WebLogin().loginToApplication(getScriptData.get("UserName").toString(),
									getScriptData.get("Password").toString());

		functions.clickObject(homePage.btn_addToCard_backpack,"Add To Card Button");

		String itemCount= functions.getText(homePage.card_itemCount,"Card IteamCount","");

		Assert.assertEquals(itemCount,"1");

	}

	@Test(description = "Adding Two Item to Cart")
	public void addingTwoItemToCart() throws Exception {

		String testCaseName= new Throwable().getStackTrace()[0].getMethodName().toString();
		ConcurrentHashMap<String, Object> getScriptData= getData.get(testCaseName);

		App().WebLogin().loginToApplication(getScriptData.get("UserName").toString(),
				getScriptData.get("Password").toString());

		functions.clickObject(homePage.btn_addToCard_backpack,"Add To Card Button-Backpack");

		functions.clickObject(homePage.btn_addToCard_bikeLight,"Add To Card Button-BikeLight");

		String itemCount= functions.getText(homePage.card_itemCount,"Card IteamCount","");

		Assert.assertEquals(itemCount,"1");

	}


}