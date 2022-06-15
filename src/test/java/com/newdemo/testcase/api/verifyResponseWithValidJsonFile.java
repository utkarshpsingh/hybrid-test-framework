package test.java.com.newdemo.testcase.api;

import main.java.com.newdemo.framework.controller.ApiController;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


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
