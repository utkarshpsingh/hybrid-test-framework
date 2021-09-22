package com.newdemo.framework.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.WebDriver;

import com.newdemo.framework.base.Utilites;

import io.restassured.path.json.JsonPath;

import com.newdemo.framework.base.BaseSetupClass;


public class WebData 
{
	Utilites Functions = null;
	BaseSetupClass enviornment ;
		
	//============================FIELD VARIABLES DECLARATION=====================================
	public String strDTURL;
	//public HashMap <String, Object> getData;
	
	
	public WebData() throws Exception
	{
		Functions = new Utilites();
		//getData= getExcelData;
							
		JsonPath jsonPath = new JsonPath(new File(BaseSetupClass.getEnviornment()));
		String getEnvironment= System.getProperty("environment");
		strDTURL= jsonPath.get("Environment."+getEnvironment+".WEB.URL");
		
		
	}

}
