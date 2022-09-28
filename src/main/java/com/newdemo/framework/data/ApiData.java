package main.java.com.newdemo.framework.data;

import java.io.File;

import main.java.com.newdemo.framework.base.BaseSetupClass;
import main.java.com.newdemo.framework.base.Utilites;
import io.restassured.path.json.JsonPath;

public class ApiData {
	
	Utilites Functions = null;
	BaseSetupClass enviornment ;

	//====================FIELD VARIABLES DECLARATION=============================
	public String strAPIURL = "";
	public String strAPIUserName = "";
	public String strAPIPassword = "";

	
	public ApiData() throws Exception
	{
		
		Functions = new Utilites();
		
		JsonPath jsonPath = new JsonPath(new File(BaseSetupClass.getEnvironment()));
		String getEnvironment= System.getProperty("environment");
		strAPIURL= jsonPath.get("Environment."+getEnvironment+".API.URL_ENDPOINT");
		  strAPIUserName = jsonPath.get("Environment."+getEnvironment+".API.UserName");
		    strAPIPassword = jsonPath.get("Environment."+getEnvironment+".API.Password");
	}

	

}
