package com.newdemo.framework.base;


import java.io.BufferedReader;
import java.io.File;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import javax.script.ScriptException;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ReuseableFunctions

{

	public Properties objCMPProperties = new Properties();
	public Utilites objUtilitess = new Utilites();

//	private final static AmazonS3 amazonS3Client = AmazonS3ClientBuilder.standard().build();


	public String strErrorMsg = "";


	public static WebDriver driver;
	protected JavascriptExecutor JSExecutor;
	
	public WebElement waitElement;

	public WebDriverWait wait;
	
	
	public enum waitCondition {
			toBeVisible, toBeClickable, toBeInvisible, tobePresent
		}
	
//	static Screen s = new Screen();
	
	//========================================CONSTRUCTOR FOR COMPONENT REUSABLE FUNCTIONS=================
	public ReuseableFunctions(WebDriver objTempWebDriver) throws Exception
	{
	
		//==========================INITIALIZE THE WEBDRIVER OBJECT INSIDE COMPONENT REUSABLE FUNCTIONS======
	      driver = objTempWebDriver;
	      JSExecutor = (JavascriptExecutor)driver;
	      wait = new WebDriverWait(driver,20);

	}

	public synchronized Boolean launchURL(String strURL) throws Exception
	{
		try
		{
			driver.get(strURL);
					System.out.println(strURL + " launched in browser");
					return true;
		}
		catch(Exception objException)
		{
					strErrorMsg = objUtilitess.GetExceptionNDisplay(objException, true);
						System.err.println(strErrorMsg);
							return false;
		}
	}

	
	
	public synchronized Boolean clickObject(WebElement objWebElement, String strObjectName) throws Exception
	{
		try
		{
			waitForElement(objWebElement);
				objWebElement.click();
					System.out.println(strObjectName +  " "+  "Clicked");
			return true;
		}
		catch(Exception objException)
		{
			strErrorMsg = objUtilitess.GetExceptionNDisplay(objException, true);
				System.err.println(strObjectName +  " "+  "Not Clicked");
			return false;
		}
	}


	
	
	public synchronized Boolean clickObjectJavascript(WebElement objWebElement, String strObjectName) throws Exception
	{
		try
		{
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", objWebElement);
					System.out.println(strObjectName +  " "+  "Clicked");
			return true;
		}
		catch(Exception objException)
		{
			strErrorMsg = objUtilitess.GetExceptionNDisplay(objException, true);
				System.err.println(strObjectName +  " "+  "Not Clicked");
			return false;
		}
	}
	
	
	public synchronized Boolean clickObjectAction(WebElement objWebElement, String strObjectName) throws Exception
	{
		try
		{
			waitForElement(objWebElement);
			Actions actions = new Actions(driver);
	        actions.moveToElement(objWebElement).build().perform();;
			Thread.sleep(1000);
					System.out.println(strObjectName +  " "+  "Clicked");
			return true;
		}
		catch(Exception objException)
		{
			strErrorMsg = objUtilitess.GetExceptionNDisplay(objException, true);
				System.err.println(strObjectName +  " "+  "Not Clicked");
			return false;
		}
	}
	

	public static boolean isAlertPresent(){
	      try{
	          driver.switchTo().alert();
	          return true;
	      }catch(NoAlertPresentException ex){
	          return false;
	      }
	}
	




	public synchronized Boolean typeValue(WebElement objWebElement, String strObjectName, String strInputValue   ) throws Exception

	{
		try
		{
			objWebElement.sendKeys(strInputValue);
				Thread.sleep(1000);
					System.out.println(strInputValue + " "+ "Typed in " + " "+ strObjectName );
					return true;
		}
		catch(Exception objException)
		{
			strErrorMsg = objUtilitess.GetExceptionNDisplay(objException, true);
			
				System.err.println(strInputValue + " "+ "Not typed " );
				return false;
		}
	}
	
	



	public synchronized Boolean SelectFromDropDown(WebElement objWebElement, String strObjectName, String strValue) throws Exception

	{
		try
		{
			Select dropdown = new Select(objWebElement);
			Thread.sleep(2000);
			dropdown.selectByValue(strValue);
			//dropdown.selectByVisibleText(strValue);
				Thread.sleep(2000);
					System.out.println(strValue + " "+ "Selected from DropDown ");
					return true;
		}
		catch(Exception objException)
		{
			strErrorMsg = objUtilitess.GetExceptionNDisplay(objException, true);
			
			System.err.println(strValue + " "+ "Not Selected from DropDown \"" );

				return false;
		}
	}

	public Boolean SelectFromDropDownVisibleText(WebElement objWebElement, String strObjectName, String strValue) throws Exception
	{
		try
		{
			Select dropdown = new Select(objWebElement);
		//	dropdown.selectByValue(strValue);
			dropdown.selectByVisibleText(strValue);
				Thread.sleep(2000);
					System.out.println(strValue + " "+ "Selected from DropDown ");
					return true;
		}
		catch(Exception objException)
		{
			strErrorMsg = objUtilitess.GetExceptionNDisplay(objException, true);
			
			System.err.println(strValue + " "+ "Not Selected from DropDown \"" );
				return false;
		}
	}
	
	public Boolean SelectFromDropDownbyindex (WebElement objWebElement, String strObjectName, int strindex) throws Exception
	{
		try
		{
			Select dropdown = new Select(objWebElement);
		//	dropdown.selectByValue(strValue);
			dropdown.selectByIndex(strindex);
				Thread.sleep(2000);
					System.out.println(strindex + " "+ "Selected from DropDown ");
					return true;
		}
		catch(Exception objException)
		{
			strErrorMsg = objUtilitess.GetExceptionNDisplay(objException, true);
			
				System.err.println(strindex + " "+ "Not Selected from DropDown \"" );
				return false;
		}
	}
	
	


		
	
	public synchronized Boolean SelectFromDropDownIndex(WebElement objWebElement, String strObjectName, int strIndex) throws Exception
	{
		try
		{
			Select dropdown = new Select(objWebElement);
			dropdown.selectByIndex(strIndex);
				Thread.sleep(1000);
					System.out.println(strIndex + " "+ "Selected from DropDown ");
					return true;
		}
		catch(Exception objException)
		{
			strErrorMsg = objUtilitess.GetExceptionNDisplay(objException, true);
			
			System.err.println(strIndex + " "+ "Not Selected from DropDown \"" );
				return false;
		}
	}

	
	public synchronized Boolean clearNTypeValue(WebElement objWebElement, String strInputValue, String strObjectName) throws Exception

	{
		try
		{
			objWebElement.clear(); 
				Thread.sleep(500);
					objWebElement.sendKeys(strInputValue);
			return true;
		}
		
		catch(Exception objException)
			{
				strErrorMsg = objUtilitess.GetExceptionNDisplay(objException, true);
			
					System.err.println(strInputValue + " "+ "Not typed " );
							return false;
		}
	}

	


	
	public synchronized boolean waitForElement(WebElement objWebElement) throws Exception {

		try
		{
		waitElement = wait.until(ExpectedConditions.visibilityOf(objWebElement));
			waitElement.isDisplayed();
			
				return true;		
		}catch(Exception objException)
		{
			strErrorMsg = objUtilitess.GetExceptionNDisplay(objException, true);
		//System.err.println("Unable to launch URL=>" + strURL + " in browser.<BR>Error message=>" + strErrorMsg);
				System.err.println(strErrorMsg);
				return false;	
	}
	
	}
	
public synchronized void waitForElement(waitCondition condition, WebElement element) throws Exception {
		
		switch (condition) {
		case tobePresent:
			wait.until(ExpectedConditions.presenceOfElementLocated(getLocatorFromElement(element)));
			break;
		case toBeVisible:
			wait.until(ExpectedConditions.visibilityOfElementLocated(getLocatorFromElement(element)));
			break;
		case toBeInvisible:
			wait.until(ExpectedConditions.invisibilityOfElementLocated(getLocatorFromElement(element)));
			break;
		case toBeClickable:
			wait.until(ExpectedConditions.elementToBeClickable(getLocatorFromElement(element)));
			break;
		default:
			break;
		}		
	}
	

	
	
	
	
	

	public By getLocatorFromElement(WebElement element) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Object proxyOrigin = FieldUtils.readField(element, "h", true);
        Object locator1 = FieldUtils.readField(proxyOrigin, "locator", true);
        Object findBy = FieldUtils.readField(locator1, "by", true);
		return (By) findBy;
		
	}
		
	public synchronized String getText(WebElement objWebElement, String strObjectName, String attributeName) throws Exception
	{
		try
		{  waitForElement(objWebElement);
			String Text = objWebElement.getText();

				if( Text == null) {
					Text = objWebElement.getAttribute(attributeName);
					 
				}
					System.out.println(Text + " "+ "Extracted from " + " "+ strObjectName );
					return Text;
		}
		catch(Exception objException)
		{
			strErrorMsg = objUtilitess.GetExceptionNDisplay(objException, true);
			
			System.err.println("Couldn't Extract Text " );
				
		}
		return null ;
	}
	

	
	


	public synchronized void ElementPresentorEnabled(WebElement objWebElement, String  getStatus, String strObjectName) throws InterruptedException{
        try{
            if(getStatus.equals("Enabled")){
                if(objWebElement.isEnabled())
                	
                	System.out.println(strObjectName + "" + " is Enabled on the page");
                	  
            }
            if(getStatus.equals("Present")){
                if(objWebElement.isDisplayed())
                	
                	System.out.println(strObjectName + "" + " is Present on the page");
                
            }
        }catch(org.openqa.selenium.NoSuchElementException nse){
        	System.err.println(nse);
    		
        }
	}

	
	public static void renameDownloadedfiles( String portfolio, String Env, File difference ) throws ScriptException, IOException, InterruptedException {
	
		 
		 String folderName = System.getProperty("user.dir") + "/Downloads/";


	if (difference.exists() ){
			difference.delete();
					System.err.println("old difference.csv File deleted");
	}else {
						
	}	
	
	File[] listFiles = new File(folderName).listFiles();

	for (int i = 0; i < listFiles.length; i++) {

	    if (listFiles[i].isFile()) {
	        String fileName = listFiles[i].getName();
	        	if (fileName.startsWith("cmbs_loan_by_loan_batch_") && fileName.endsWith(".xlsx")) {
	        		File file	= new File(folderName + fileName);
	        			System.out.println(file);
	        			file.renameTo(new File(folderName + portfolio + "LBL-" + Env + ".xlsx"));
	        					System.err.println("File Renamed to :" + " " + folderName + portfolio + "LBL-" + Env + ".xlsx");
	        			
	        					}else
	        						
	        					 if (fileName.startsWith("portfolio_calculator_") && fileName.endsWith(".xlsx")){
	        						 File file	= new File(folderName + fileName);
	        		        			System.out.println(file);
	        		        			file.renameTo(new File(folderName + portfolio + Env + ".xlsx"));
	        		        			
	    	        					System.err.println("File Renamed to :" + " " + folderName + portfolio + Env + ".xlsx");
	
	        					 }
	                 
	    }
	}
	
	 }


 public static void DeleteoldDownloadedfiles( String portfolio, String Env, File difference ) throws ScriptException, IOException, InterruptedException {
	
		 
		 String folderName = System.getProperty("user.dir") + "/Downloads/";

	
	File[] listFiles = new File(folderName).listFiles();


	for (int i = 0; i < listFiles.length; i++) {

	    if (listFiles[i].isFile()) {
	        String fileName = listFiles[i].getName();
	        	if (fileName.startsWith(portfolio + "LBL-" ) && fileName.endsWith(".xlsx")) {
	        		File fileLBL	= new File(folderName + portfolio + "LBL-" + Env + ".xlsx");
	        				fileLBL.delete();
	        					System.err.println(fileLBL + " " +"Old file deleted");
	        					
	        					}else
	        						
	        					 if (fileName.startsWith(portfolio) && fileName.endsWith(".xlsx")){
	        							File file	= new File(folderName + portfolio  + Env + ".xlsx");
	        	        				file.delete();
	        	        				System.err.println(file + " " +"Old file deleted 1");
	        					 } else 
	        						 
	        					if ( fileName.startsWith("difference" + portfolio) && fileName.endsWith(".xlsx")){
        							
	        						difference.delete();
        	        				System.err.println(portfolio + "Old differenes file deleted 2 ");
        					 } else
        						 
        						 if(fileName.startsWith("difference" + portfolio+ "-LBL") && fileName.endsWith(".xlsx")) {
        							 difference.delete();
         	        				System.err.println(portfolio+ "LBL OLD differenes file deleted 3");
        						 }
	                 
	    }
	}
	
	 } 
	 
 public static void convertCSV( ) throws ScriptException, IOException, InterruptedException {
  try {
         CommandLine cmdLine = new CommandLine("python3");
		 cmdLine.addArgument(System.getProperty("user.dir") + "/Config/test.py");
		 DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

		 ExecuteWatchdog watchdog = new ExecuteWatchdog(60*1000);
		 Executor executor = new DefaultExecutor();
		 executor.setExitValue(1);
		 executor.setWatchdog(watchdog);
		 executor.execute(cmdLine, resultHandler);

		 // some time later the result handler callback was invoked so we
		 // can safely request the exit value
		 resultHandler.waitFor();
		 
  	} catch (Exception ex) {
  		System.out.println(ex.getMessage() + "Found Exception");
  		}
	 
 } 
	 
	
  public static void runpython(String pythonFilePath, File difference ) throws ScriptException, IOException, InterruptedException {
  
	
	 CommandLine cmdLine = new CommandLine("python3");
	 	cmdLine.addArgument(pythonFilePath);
	 		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

	 			ExecuteWatchdog watchdog = new ExecuteWatchdog(60*1000);
	 				Executor executor = new DefaultExecutor();
	 					executor.setExitValue(1);
	 						executor.setWatchdog(watchdog);
	 							executor.execute(cmdLine, resultHandler);

	 
	                                resultHandler.waitFor();
	
	
	                                	Thread.sleep(5000);
	
		    if(difference.exists()){
				
				double bytes = difference.length();
					double kilobytes = (bytes / 1024);
						double megabytes = (kilobytes / 1024);
							System.out.println("kilobytes : " + megabytes);
                   if (kilobytes == 0) {
                	   System.out.println("No difference in the CSV Found");
                   } else {
                	   System.out.println("Difference Found in the CSV, File created at:" +  difference);
                	  
                   }
				
			}else{
				 System.out.println("File does not exists!");
			}
		    
		    
		}


  		      /*   public static void ReadfromS3(String BucketName, String Key ) throws IOException {
      
        			 S3Object object = amazonS3Client.getObject(BucketName, Key);
        			      InputStreamReader objectData = new InputStreamReader (object.getObjectContent());
        			     	BufferedReader reader = new BufferedReader(objectData);
        			        	System.out.println(reader.lines());
									objectData.close();
        }

  		 		


  public void sikuliclick(String LocatorImage) throws Exception { 
	  			
	 		try {	
	 			
 					Finder f = new Finder(LocatorImage);	
 						Thread.sleep(2000);
 							if(f.find(new Pattern(LocatorImage)) != null) {	 
 						s.click(LocatorImage);
 					ATUReports.add(" Image clciked", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));					
 				
 								}
	 		     }
 					 catch(Exception objException)	 			   
	 			{
 						 ATUReports.add(" Image not found", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
 						 	System.out.println(strErrorMsg);
 			    	 }
               }
  
  
  	public  void sikuliImageExist(String LocatorImage ) {

  							try {
  									Match m = s.exists(new Pattern(LocatorImage).exact());
  										Thread.sleep(2000);
  											if(m != null) {	 
  												s.click(LocatorImage);
					      ATUReports.add(" Image Displayed in browser", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));							      
				}
	         }
	         catch(Exception objException)	 			   
	         
  							{	
	        	 				ATUReports.add(" Image not Displayed in browser", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	        	 					System.out.println(strErrorMsg);
	         	}
		
     		}
     		*/
 
  	
  public String waitUntilDonwloadCompleted() throws InterruptedException {
      // Store the current window handle
      String mainWindow = driver.getWindowHandle();

      // open a new tab
      JavascriptExecutor js = (JavascriptExecutor)driver;
      js.executeScript("window.open()");
     // switch to new tab
    // Switch to new window opened
      for(String winHandle : driver.getWindowHandles()){
          driver.switchTo().window(winHandle);
      }
     // navigate to chrome downloads
      driver.get("chrome://downloads");

     
      // wait until the file is downloaded
      Long percentage = (long) 0;
      while ( percentage!= 100) {
          try {
              percentage = (Long) JSExecutor.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('#progress').value");
              //System.out.println(percentage);
          }catch (Exception e) {
            // Nothing to do just wait
        }
          Thread.sleep(1000);
          if(percentage == 0) {
          	break;
          }
      }
     // get the latest downloaded file name
      String fileName = (String) JSExecutor.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
     // get the latest downloaded file url
      String sourceURL = (String) JSExecutor.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').href");
      // file downloaded location
      String donwloadedAt = (String) JSExecutor.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div.is-active.focus-row-active #file-icon-wrapper img').src");
     // print the details
      System.out.println(fileName);
      System.out.println(sourceURL);
     // close the downloads tab2
      driver.close();
     // switch back to main window
      driver.switchTo().window(mainWindow);
      return fileName;
  }
  	

	public synchronized boolean isFileExist(String dirPath, String fileName) throws InterruptedException {
  		File file = Paths.get(dirPath, fileName).toFile();
  		 
  		long end = System.currentTimeMillis() + 10000;
  		while(System.currentTimeMillis() < end) {
  		  //checking if file exist
  			Boolean fileExist = false;
  			if (BaseSetupClass.executionEnv.equalsIgnoreCase("cloud")) {
  				fileExist = (boolean) JSExecutor.executeScript("lambda-file-exists=" + fileName);
  			}else {
  				fileExist = file.exists();
  			}
  			
  			if(fileExist)
  				return true;
  		  // pause to avoid churning
  		  Thread.sleep( 500 );
  		}
  		System.err.println("waited for 10 seconds for file " + file.getPath() + "to exist.");
  		return false;
  	}
  	
  	public synchronized boolean deleteFile(String dirPath, String fileName) throws Exception {
  		try {
  			if(fileName.equals("*")) {
  				FileUtils.cleanDirectory(new File(dirPath));
  				return true;
  			}
  			File file = Paths.get(dirPath, fileName).toFile();
  			//checking if file exist and not a directoty
  			if(file.exists() && !file.isDirectory()) {
  				file.delete();
  				return true;
  			}
  			return false;
		} catch (Exception e) {
			strErrorMsg = objUtilitess.GetExceptionNDisplay(e, true);
			System.err.println("not able to delete file \n"+strErrorMsg );
				return false;
		}
  	}
  	
  	public synchronized boolean scrollIntoView(WebElement element, String strObjectName) throws Exception {
  		try
		{
  			JSExecutor.executeScript("return arguments[0].scrollIntoView({inline:'center'});", element );
			System.out.println( strObjectName+ " "+ "scrolled into view ");
					return true;
		}
		catch(Exception objException)
		{
			strErrorMsg = objUtilitess.GetExceptionNDisplay(objException, true);
			
			System.err.println(strObjectName + " "+ "not bale to scrolled into view \"" );
				return false;
		}
  	}
  	
  	public static boolean switchToFrame(WebElement element){
	      try{
	          driver.switchTo().frame(element);
	          return true;
	      }catch(Exception ex){
	          return false;
	      }
	}
	
	public static boolean switchToMainFrame(){
	      try{
	          driver.switchTo().parentFrame();
	          return true;
	      }catch(Exception ex){
	          return false;
	      }
	}
	

	public void switchToNewWindow() {
		ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(newTab.get(1));
	}
	
	public void switchToOldWindow() {
		ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
		driver.close();
	    driver.switchTo().window(newTab.get(0));
	}
	
	public WebElement expandRootElement(WebElement element) {
		WebElement ele = (WebElement) ((JavascriptExecutor)driver)
	.executeScript("return arguments[0].shadowRoot", element);
		return ele;
	}
	public void moveToElement(WebElement objWebElement, String strObjectName) {
 		 //Instantiate Action Class        
       Actions actions = new Actions(driver);
   	//Mouse hover action on element 
   	actions.moveToElement(objWebElement).perform();
 	}
	
	public String getBrowserUrl(){
	      return driver.getCurrentUrl();
	}

	//===========================MAIN METHOD==============================================
	public static void main(String[] args)
	{

	}

}
