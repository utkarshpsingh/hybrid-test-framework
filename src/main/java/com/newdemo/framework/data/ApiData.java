package com.newdemo.framework.data;

import java.io.FileInputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Properties;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.newdemo.framework.base.BaseSetupClass;
import com.newdemo.framework.base.Utilites;

public class ApiData {
	
	Utilites Functions = null;
	BaseSetupClass enviornment ;

	//===================DECLARE PARAMETERIZATION VARIABLES======================
	HSSFWorkbook objWB = null;
	HashMap hmParamsNValues = null;
	int intParamInputRow = 0;
	
	String strParamGetSheetName = "";
	//====================FIELD VARIABLES DECLARATION=============================
	public String strAPIURL = "";
	public String strAPIUserName = "";
	public String strAPIPassword = "";
	public int Range=0;
	public String strParamGetInputSheet ="";
	HSSFSheet objSHInputSheet;
	
	public ApiData(String strParametersNValues) throws Exception
	{
		
		Functions = new Utilites();
				
	//=====================================PARAMETERIZATION STEPS==========================================
		hmParamsNValues = Functions.SplitNStoreParamsInHashMap(strParametersNValues);
		intParamInputRow = Integer.parseInt((String) hmParamsNValues.get("InputDataRow"));
		//Reading the sheetName
		strParamGetSheetName = (String) hmParamsNValues.get("SheetName2");
		if(hmParamsNValues.get("Range")!=null) {
			Range = Integer.parseInt((String)hmParamsNValues.get("Range"));
		}
		
		if (!(BaseSetupClass.getEnviornment()==null)) {
			objWB = new HSSFWorkbook(new FileInputStream(BaseSetupClass.getEnviornment()));
				objSHInputSheet = objWB.getSheet(strParamGetSheetName);
		
		}else {
			
			Properties objProperties = new Properties();
				try {
					strParamGetInputSheet = (String) hmParamsNValues.get("inputsheet");
					 objProperties.load(new FileReader(System.getProperty("user.dir") + "/Config/CONFIG.properties"));
					  String DataSheetPath = System.getProperty("user.dir")+"/Input/"+strParamGetInputSheet;
					   objWB = new HSSFWorkbook(new FileInputStream(DataSheetPath));
					     objSHInputSheet = objWB.getSheet(strParamGetSheetName);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		
		strAPIURL = Functions.GetCellValueForRowNum(objSHInputSheet, "URL", intParamInputRow);
		  strAPIUserName = Functions.GetCellValueForRowNum(objSHInputSheet, "UserName", intParamInputRow);
		    strAPIPassword = Functions.GetCellValueForRowNum(objSHInputSheet, "Password", intParamInputRow);
	}

	

}
