package main.java.com.newdemo.framework.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {


	@FindBy(xpath = "//span[contains(text(),'Products')]") public WebElement projectHeader;
	@FindBy(xpath = "//div[contains(text(),'Backpack')]//ancestor::div[@class='inventory_item_description']//button") public WebElement btn_addToCard_backpack;
	@FindBy(xpath = "//div[contains(text(),'Bike Light')]//ancestor::div[@class='inventory_item_description']//button") public WebElement btn_addToCard_bikeLight;
	@FindBy(xpath = "//span[@class='shopping_cart_badge']") public WebElement card_itemCount;


}
