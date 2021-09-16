package com.newdemo.testcase.api;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.newdemo.framework.controller.ApiController;


public class verifyResponseWithValidJsonFile {
	ApiController ApiController;
	
	@Parameters({"ParameterNValue"})
	@Test()
	public void verifyResponseWithValidJsonStructure(String ParameterNValue) throws Exception
	{
			
		String strDescription = "verify Response With valid Json File";
		ApiController = new ApiController(ParameterNValue,"");
		

 		// ApiController.verifyResponseCodeValidJson();
        		
	}
}
