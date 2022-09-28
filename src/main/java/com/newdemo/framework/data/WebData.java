package main.java.com.newdemo.framework.data;

import java.io.File;

import main.java.com.newdemo.framework.base.BaseSetupClass;
import main.java.com.newdemo.framework.base.Utilites;
import io.restassured.path.json.JsonPath;


public class WebData 
{
	Utilites functions = null;
	BaseSetupClass enviornment ;
		
	//============================FIELD VARIABLES DECLARATION=====================================
	public String strDTURL;
	//public HashMap <String, Object> getData;
	
	
	public WebData() throws Exception
	{
		functions = new Utilites();

		JsonPath jsonPath = new JsonPath(new File(BaseSetupClass.getEnvironment()));
		String getEnvironment= System.getProperty("environment");
		strDTURL= jsonPath.get("Environment."+getEnvironment+".WEB.URL");
		
		
	}

}
