package com.newdemo.framework.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.WebDriver;

import com.newdemo.framework.base.Utilites;
import com.newdemo.framework.base.BaseSetupClass;


public class LoginData 
{
	Utilites Functions = null;
	BaseSetupClass enviornment ;
	
	//===================DECLARE PARAMETERIZATION VARIABLES======================
	HSSFWorkbook objWB = null;
	HashMap hmParamsNValues = null;
	int intParamInputRow = 0;
	
	//====================FIELD VARIABLES DECLARATION=============================
	public String strDTURL = "";
	public String strDTUserName = "";
	public String strDTPassword = "";
	
	public LoginData(String strParametersNValues) throws Exception
	{
		Functions = new Utilites();
		
		
		//=============================PARAMETERIZATION STEPS==========================================
		hmParamsNValues = Functions.SplitNStoreParamsInHashMap(strParametersNValues);
		intParamInputRow = Integer.parseInt((String) hmParamsNValues.get("InputDataRow"));
		
		objWB = new HSSFWorkbook(new FileInputStream(BaseSetupClass.getEnviornment()));
		
		HSSFSheet objSHInputSheet = objWB.getSheet("Loan");
		
		strDTURL = Functions.GetCellValueForRowNum(objSHInputSheet, "URL", intParamInputRow);
	
		strDTUserName = Functions.GetCellValueForRowNum(objSHInputSheet, "UserName", intParamInputRow);
		strDTPassword = Functions.GetCellValueForRowNum(objSHInputSheet, "Password", intParamInputRow);
	}

}
