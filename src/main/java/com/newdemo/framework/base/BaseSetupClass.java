package main.java.com.newdemo.framework.base;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import main.java.com.newdemo.framework.controller.ApplicationController;
import main.java.com.newdemo.framework.controller.TestScenarioDataController;
import org.apache.xmlbeans.SystemProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.Reporter;
import org.testng.annotations.*;

public class BaseSetupClass {
	
	Properties objProperties = new Properties();
	{
		try {
			objProperties.load(new FileReader(System.getProperty("user.dir") + "/Config/CONFIG.properties"));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ApplicationController App = null;
	public Utilites commonFunctions = new Utilites();
	public TestScenarioDataController dataController = null;
	private ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> getData;

	// ==========================BROWSER VARIABLES============================================
	public static final String CHROME_DRIVER_KEY = "webdriver.chrome.driver";
	public static final String CHROME_DRIVER_EXE = "/Lib/chromedriver.exe";
	public static final String EDGE_DRIVER_KEY = "webdriver.ie.driver";
	public static final String EDGE_DRIVER_EXE = "./Lib/ieDriverServer.exe";
	public static final String FIREFOX_DRIVER_KEY = "webdriver.geckoDriver.driver";
	public static final String FIREFOX_DRIVER_EXE = "./Lib/geckoDriver.exe";

	public static ThreadLocal<WebDriver> wdriver = new ThreadLocal<WebDriver>();

	public String browser = null;

	WebDriver localDriver = null;
	protected String env = null;
	private static String enviornment = null;
	public static String executionEnv = null;
	public static String ConfigFile = "Config/CONFIG.properties";
	public Properties config;

	public static String downloadFilepath = null;
	public static String jsonFilepath = null;

	private HashMap<String, String> getCapabilitiesfromParams(String params) {
		HashMap<String, String> cap = new HashMap<String, String>();

		for (String pair : params.split(";")) {
			String[] entry = pair.split(":");
			cap.put(entry[0], entry[1]);
		}

		return cap;
	}

	public enum BROWSER {
		firefox, ie, MicrosoftEdge, chrome, safari
	}

	@Parameters("Execute")
	@BeforeSuite
	public synchronized void loadTestData(String execute) throws Exception {

		dataController= new TestScenarioDataController();
		getData= dataController.getDataForSheetTestData();

		//Selenium-Grid environment-up
		if (execute.equalsIgnoreCase("cloud")) {
			try {
				Runtime runtime = Runtime.getRuntime();
				String filepath=System.getProperty("user.dir")+"/Config/seleniumGridUp.bat";
				//runtime.exec("cmd /c start "+filepath);
				//Thread.sleep(40000);

			} catch (Exception ex) {
				System.err.println("Error: Selenium Grid environment fail to get UP, exception: " + ex.getMessage());
			}
		}
	}

	@Parameters({ "remoteurl", "Execute", "capabilities", "environment" })
	@BeforeTest
	public synchronized void LaunchBrowser(String remoteUrl, String execute, String capabilities, String environment) throws Exception {

		if(getData==null){
			dataController= new TestScenarioDataController();
			getData= dataController.getDataForSheetTestData();
		}

		System.setProperty("executionPlatform", execute);
		System.setProperty("environment", environment);
		env = SystemProperties.getProperty("environment", environment);
		BaseSetupClass.jsonFilepath = objProperties.getProperty("InputDatasheetPath");
		setEnvironment(BaseSetupClass.jsonFilepath + "Environment.json");
		
		BaseSetupClass.executionEnv = execute.toLowerCase();

		HashMap<String, String> capMap = this.getCapabilitiesfromParams(capabilities);
		
		BROWSER selectedBrowser = null;
		selectedBrowser = BROWSER.valueOf(capMap.get("browserName"));

		if (execute.equalsIgnoreCase("cloud")) {
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setBrowserName(selectedBrowser.toString());
			//caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			String URL = remoteUrl;
			if(selectedBrowser == BROWSER.ie) {
				//handle for specific browser
			}
			localDriver = new RemoteWebDriver(new URL(URL), caps);

		} else {

			if (selectedBrowser == null) {
				throw new RuntimeException("Unknown browser variable specified!!!!!!");
			}

			try {

				switch (selectedBrowser) {
				case firefox: {
					System.setProperty(FIREFOX_DRIVER_KEY, FIREFOX_DRIVER_EXE);
					localDriver = new FirefoxDriver();
					break;
				}

				case chrome: {
					downloadFilepath = Paths.get(System.getProperty("user.dir"), "Downloads").toString();
					File directory = new File(downloadFilepath);
					if (!directory.exists()) {
						directory.mkdir();
					}
					System.out.println(downloadFilepath);
					HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
					chromePrefs.put("profile.default_content_settings.popups", 0);
					chromePrefs.put("download.default_directory", downloadFilepath);
					ChromeOptions options = new ChromeOptions();
					options.setExperimentalOption("prefs", chromePrefs);
					options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					options.setCapability("chrome.switches", Arrays.asList("--ignore-certificate-errors"));
					DesiredCapabilities cap = new DesiredCapabilities();
					cap.setBrowserName("chrome");
					options.merge(cap);
					cap.setCapability(ChromeOptions.CAPABILITY, options);
					//ChromeDriverService service = new ChromeDriverService.Builder()
					//		.usingDriverExecutable(new File(objProperties.getProperty("ChromeDriverPath")))
					//		.usingAnyFreePort().build();
					System.setProperty(CHROME_DRIVER_KEY, System.getProperty("user.dir")+ CHROME_DRIVER_EXE);
					localDriver = new ChromeDriver(options);

					break;

				}
				case ie: {
					System.setProperty(EDGE_DRIVER_KEY, EDGE_DRIVER_EXE);
					localDriver = new InternetExplorerDriver();
					break;
				}

				default: {
					System.err.println("Error: NO DRIVER INITIALISED");
				}
				}
			} catch (Throwable exp) {
				Reporter.log("Exception in browser initialization!!! : " + exp.getMessage());
			}


			String OS = System.getProperty("os.name").toLowerCase();
			if (OS.indexOf("win") >= 0) {
				localDriver.manage().window().maximize();
			}
			if (OS.indexOf("mac") >= 0) {
				localDriver.manage().window().fullscreen();
			}
		}
		
	setDriver(localDriver);
	SessionId session = ((RemoteWebDriver) wdriver.get()).getSessionId();
	System.out.println("Session id: " + session.toString());



	}//LaunchBrowser

	public static void setDriver(WebDriver wdriver) {
		BaseSetupClass.wdriver.set(wdriver);
	}

	public ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> getTestData(){
		return getData;
	}

	public static WebDriver getDriver() {
		return wdriver.get();
	}

	public ApplicationController App() {
		if (App == null) {
			App = new ApplicationController(getDriver());
		}
		return App;
	}

	@AfterMethod
	public void afterMethod() {
		this.App = null;
	}

	@AfterTest
	public synchronized void tearDown() throws Exception {
		wdriver.get().quit();
	}

	public static String getEnvironment() {
		return enviornment;
	}

	public void setEnvironment(String environment) {
		BaseSetupClass.enviornment = environment;
	}

}