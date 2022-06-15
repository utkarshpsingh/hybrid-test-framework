package main.java.com.newdemo.framework.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Properties;
import main.java.com.newdemo.framework.base.BaseSetupClass;
import main.java.com.newdemo.framework.base.Utilites;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
		
		JsonPath jsonPath = new JsonPath(new File(BaseSetupClass.getEnviornment()));
		String getEnvironment= System.getProperty("environment");
		strAPIURL= jsonPath.get("Environment."+getEnvironment+".API.URL_ENDPOINT");
		  strAPIUserName = jsonPath.get("Environment."+getEnvironment+".API.UserName");
		    strAPIPassword = jsonPath.get("Environment."+getEnvironment+".API.Password");
	}

	

}
