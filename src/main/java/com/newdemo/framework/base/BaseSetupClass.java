package com.newdemo.framework.base;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.newdemo.framework.controller.ApplicationController;

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
	public String strDTParametersNValues = "";
	public String ParameterNValue = null;
	public static String strMainParametersNValues = "";
	public Utilites commonFunctions = new Utilites();

	// ==========================BROWSER VARIABLES============================================
	public static final String CHROME_DRIVER_KEY = "webdriver.chrome.driver";
	public static final String IE_DRIVER_KEY = "webdriver.ie.driver";
	public static final String IE_DRIVER_EXE = "./Lib/IEDriverServer.exe";
	
	private PropertiesConfiguration context;

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

	public Configuration getContext() {
		return (Configuration) this.context;
	}

	public String getString(String key) {
		return this.context.getString(key);
	}

	public String getString(String key, String defVal) {
		return this.context.getString(key, defVal);
	}

	private HashMap<String, String> getCapabilitiesfromParams(String params) {
		HashMap<String, String> cap = new HashMap<String, String>();

		for (String pair : params.split(";")) {
			String[] entry = pair.split(":");
			cap.put(entry[0], entry[1]);
		}

		return cap;
	}

	public enum BROWSER {
		firefox, ie, microsoftEdge, chrome, safari
	}

	@Parameters({ "remoteurl", "Execute", "capabilities", "environment" })

	@BeforeTest
	public synchronized void LaunchBrowser(String remoteurl, String Execute, String capabilities, String environment) throws Exception {
		
		System.setProperty("ExectionPlatform", Execute);
		System.setProperty("environment", environment);
		env = SystemProperties.getProperty("environment", environment);
		BaseSetupClass.jsonFilepath = objProperties.getProperty("InputDatasheetPath");
		setEnviornment(BaseSetupClass.jsonFilepath + "Environment.json");
		
		BaseSetupClass.executionEnv = Execute.toLowerCase();

		HashMap<String, String> capMap = this.getCapabilitiesfromParams(capabilities);
		
		BROWSER selectedBrowser = null;
		selectedBrowser = BROWSER.valueOf(capMap.get("browserName").toLowerCase());

		if (Execute.equalsIgnoreCase("cloud")) {
			DesiredCapabilities caps = new DesiredCapabilities(capMap);
			caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			caps.setCapability("timezone","UTC-08:00");
			String URL = remoteurl;
			if(selectedBrowser == BROWSER.ie) {
				caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				caps.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, "accept");
			}
			localDriver = new RemoteWebDriver(new URL(URL), caps);

		} else {

			if (selectedBrowser == null) {
				throw new RuntimeException("Unknown browser variable specified!!!!!!");
			}

			try {

				switch (selectedBrowser) {
				case firefox: {
					System.setProperty("webdriver.gecko.driver", objProperties.getProperty("FirefoxDriverPath"));
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
					DesiredCapabilities cap = DesiredCapabilities.chrome();
					options.merge(cap);
					cap.setCapability(ChromeOptions.CAPABILITY, options);
					ChromeDriverService service = new ChromeDriverService.Builder()
							.usingDriverExecutable(new File(objProperties.getProperty("ChromeDriverPath")))
							.usingAnyFreePort().build();
					System.setProperty(CHROME_DRIVER_KEY, objProperties.getProperty("ChromeDriverPath"));
					localDriver = new ChromeDriver(options);
					break;

				}
				case ie: {
					System.setProperty(IE_DRIVER_KEY, IE_DRIVER_EXE);
					localDriver = new InternetExplorerDriver();
					break;
				}

				default: {
					localDriver = new FirefoxDriver();
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

	public static String getEnviornment() {
		return enviornment;
	}

	public void setEnviornment(String enviornment) {
		BaseSetupClass.enviornment = enviornment;
	}

}