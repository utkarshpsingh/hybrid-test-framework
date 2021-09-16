package com.newdemo.framework.controller;


import java.io.File;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.newdemo.framework.data.ApiData;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class ApiController {

		
	ApiData apiData = null;
	public static String URL=	null;
	public static String username=	null;
	public static String password= null;
			
	
	public ApiController(WebDriver driver) throws Exception 
	{
		
	}

		
	public ApiController(String ParameterNValue,String extArg) throws Exception 
	{
		  apiData = new ApiData(ParameterNValue);  
		  URL= apiData.strAPIURL;
		   username= apiData.strAPIUserName;
		  password= apiData.strAPIPassword;
		
	}
	
	
	public String getTokenID() throws InterruptedException {
		
	  String bToken=null;
		JsonPath BJsonToken =null;
		
		RestAssured.baseURI = URL+"users/token";
 	     	System.out.println("TOKEN END POINT: "+RestAssured.baseURI);
	     	Response response = (Response)RestAssured.given()
	      		.relaxedHTTPSValidation()	
				.contentType("application/json")
				.body("{\r\n  \"username\": \""+username+"\",\r\n  \"password\": \""+password+"\"\r\n}")
				.when()
			.post(RestAssured.baseURI);
		
		
		int code =response.getStatusCode();
			BJsonToken = response.getBody().jsonPath();
			bToken=BJsonToken.getString("access_token");
				if(code==200) {
					System.out.println("Passed :POST TOKEN REQUEST | Status code of Token End Point: "+code);
				}else {
					  System.err.println("FAILED :POST TOKEN REQUEST | Status code of Token End Point: "+code);
				}
			Thread.sleep(3000);	
		return bToken;
	}
	
	
	public Response postRequest(String URL, String tokenID,String jsonFileName) throws InterruptedException {
		Thread.sleep(20000);
		RestAssured.baseURI = URL+"calc";
			System.out.println("END POINT: "+RestAssured.baseURI);	
			 File JsonRequestFile = new File(System.getProperty("user.dir")+"/JsonFiles/"+jsonFileName);
			 				 	
	     	Response output = (Response)RestAssured.given()
	      		.relaxedHTTPSValidation()	
				.header("Authorization", "Bearer "+tokenID )
				.contentType("application/json")
				.body(JsonRequestFile)
				.when()
			.post(RestAssured.baseURI);
	     		
	     	//System.out.println(output.asString());
	     			
	     		if (output.getStatusCode()== 200) {
	     			Thread.sleep(1000);
			  		System.out.println("PASSED : REQUEST | The Status Code is 200 For Calc End Point");
			  		  System.out.println("REQUEST- TIME | Time Taken For the Calc End Point Request : "+output.getTime());
			  			System.out.println("REQUEST- STATUS | Status Line For the Calc End Point: "+output.getStatusLine());
		  			 	
			  	}else {
			  		Thread.sleep(1000);
			  		System.err.println("FAILED : REQUEST | The Status Code is "+output.getStatusCode()+" For Calc End Point");
			  		 System.out.println("REQUEST- TIME | Time Taken For the Calc End Point Request : "+output.getTime());
		  			  System.out.println("REQUEST- STATUS | Status Line For the Calc End Point: "+output.getStatusLine());
		  			
			  	}
	     		
	  	return output;
	}

	
	public int getResponseCode(String URL, String tokenID,String jsonFileName) throws InterruptedException {
		
		RestAssured.baseURI = URL+"calc";
		 	System.out.println("END POINT: "+RestAssured.baseURI);	
		 		File JsonRequestFile = new File(System.getProperty("user.dir")+"/JsonFiles/"+jsonFileName);
		 		Thread.sleep(6000);	
	     	Response output = (Response)RestAssured.given()
	      		.relaxedHTTPSValidation()	
				.header("Authorization", "Bearer "+tokenID )
				.contentType("application/json")
				.body(JsonRequestFile)
				.when()
				.post(RestAssured.baseURI);
	     					
		System.out.println("REQUEST Status CODE | The Status Code is "+output.getStatusCode()+" For End Point");
			  		  		
  	return output.getStatusCode();
	}
	
	
	public boolean compareJsonResponse(Response output1, String output2) throws InterruptedException{
		boolean blnFlagResult =true;
		
		try {
			
			JsonParser parser = new JsonParser();
			 JsonElement actualJsonResponce = parser.parse(output1.asString());			
			  JsonElement expJsonResponce = parser.parse(output2);
			  
			boolean compareJson = actualJsonResponce.equals(expJsonResponce);
			
			if(compareJson==false) {
				
				blnFlagResult= false;
				Thread.sleep(1000);
					System.err.println("FAILED: Json Response Comparision Result: "+compareJson);
					
					 String Output1Calc= output1.asString().trim();
		     			String Output2Calc= output2.trim();
		     		  	     			
				     	if(Output1Calc.equalsIgnoreCase(Output2Calc)) {
				     		System.out.println("Json as String Comparision Result : PASSED");
				     			
				     	}else {
				     			System.err.println("Json as String Comparision Result: FAILED");
				     			
				     	}
				     	  Thread.sleep(2000);
				     	  
				     	System.err.println("Json Comparison Differences: "+StringUtils.difference(Output1Calc, Output2Calc));
			} else {
				System.out.println("PASSED: Json Response Comparision Result: "+compareJson);
			}
			
			
		}catch(Exception objException){
			
			objException.printStackTrace();
				System.err.println("EXCEPTION OCCURIED: "+objException.getMessage());
		}	
		
		return blnFlagResult;
	}

	
	public boolean compareJsonResponse(String output1, String output2) throws InterruptedException{
		boolean blnFlagResult =true;
		
		try {
						
			JsonParser parser = new JsonParser();
			 JsonElement actualJsonResponce = parser.parse(output1.toString());	
			
			  JsonElement expJsonResponce = parser.parse(output2.toString());
			 
			  boolean compareJson = actualJsonResponce.equals(expJsonResponce);
							     			
		     	if(compareJson) {
		     		System.out.println("Json as String Comparision Result : PASSED");
		     			
		     	}else {
		     		blnFlagResult= false;
		     			System.err.println("Json as String Comparision Result: FAILED");
		     			
		     	}				     	 		
			
		}catch(Exception objException){
			
			objException.printStackTrace();
				System.err.println("EXCEPTION OCCURIED: "+objException.getMessage());
		}	
		
		return blnFlagResult;
	}
		
	
	
	
	
}//Class closing
