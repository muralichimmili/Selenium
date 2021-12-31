package managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.Scenario;

import reporters.ExtentReportManager;



public class WebDriverManager {
	 public SoftAssert softAssert; 
	 public ExtentTest test;
	 public ExtentReports report=ExtentReportManager.getReports();
	 WebDriver driver;
	 Properties env;
	 Properties OR;
	public WebDriverManager()  {
		    
			env=new Properties();
			OR=new Properties();
			try {
				env.load(new FileInputStream(new File("src/test/resources/env.properties")));
				OR.load(new FileInputStream(new File("src/test/resources/ObjectRepository.properties")));
			} catch (IOException e) {
				
				e.printStackTrace();
			}	
			
			softAssert=new SoftAssert();
	}       
	
	
	public void openBrowser(String browser) {
		DesiredCapabilities cap=null;
		MutableCapabilities sauceOptions=null;
        log("Open browser "+ browser);
        
        
        if(env.getProperty("remote").equals("Y")) {
        	
			sauceOptions = new MutableCapabilities();
	        sauceOptions.setCapability("username", env.getProperty("sauce_username"));
	        sauceOptions.setCapability("access_key",env.getProperty("sauce_key"));
	        sauceOptions.setCapability("browserVersion", "latest");
	        
			if(browser.equals("edge")){
				cap=DesiredCapabilities.edge();
				EdgeOptions options=new EdgeOptions();
				cap.merge(options);
				
			}else if(browser.equals("Chrome")){
				cap=DesiredCapabilities.chrome();
				cap.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
				HashMap<String,String> chromePreferences=new HashMap<String,String>();
				chromePreferences.put("profile.password_manager_enabled", "false");
				cap.setCapability("chrome.prefs",chromePreferences);
				ChromeOptions options = new ChromeOptions();
				options.setCapability("sauce:options", sauceOptions);
				 cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				 cap.merge(options);
			}
			
			try {
				driver = new RemoteWebDriver(new URL("https://ondemand.us-west-1.saucelabs.com/wd/hub"), cap);
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        	
        	
        }else {
			System.setProperty("webdriver.chrome.driver","D:/chromedriver_win32/chromedriver.exe");
			if(browser.equals("edge")) {
				driver  = new EdgeDriver();
			}else if(browser.equals("chrome")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--start-maximized");
				options.addArguments("--disable-notifications");
				driver  = new ChromeDriver(options);
			}
        }
        
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));   latest selenium version 4  use Duration for time waits
	}
	
	
	
	 public String getEnvProperty(String key) {
	    	return env.getProperty(key);
	    } 
	 
	
	 public void navigateToApplication() {
			log("Navigating to "+getEnvProperty("url"));
			driver.navigate().to(getEnvProperty("url"));
		}
	
	
	public void click(String locatorKey) {
		
		log("Clicking on "+locatorKey );
		findElement(locatorKey).click();
		/*}catch (AssertionError e) 
{
            test.fail(e);
            softAssert.fail();
            report.flush();
           
}*/
	}
	
	public void type(String locatorKey, String data) {
		log("Typing in "+locatorKey );
		findElement(locatorKey).sendKeys(data);
	}
	
	
	public WebElement findElement(String locatorKey) {
		By locator = getLocator(locatorKey);
		WebElement e = null;
		try {
		  // present and visible
		  WebDriverWait wait = new WebDriverWait(driver,20);
		  wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		  e = driver.findElement(locator);
		}catch(Exception ex) {
			// report failure
			logFailure("Object not found "+ locatorKey,true);
		}
		return e;
	}
	
	public By getLocator(String locatorKey) {
		
		if(locatorKey.endsWith("_id"))
			return By.id(OR.getProperty(locatorKey));
		else if(locatorKey.endsWith("_name"))
			return By.name(OR.getProperty(locatorKey));
		else if(locatorKey.endsWith("_xp"))
			return By.xpath(OR.getProperty(locatorKey));
		else if(locatorKey.endsWith("_class"))
			return By.className(OR.getProperty(locatorKey));
		else if(locatorKey.endsWith("_lnktxt"))
			return By.linkText(OR.getProperty(locatorKey));
		else 
			return By.cssSelector(OR.getProperty(locatorKey));
	}
	
	/********************Validation Functions***************************/
	public boolean verifyTitle(String expectedTitleKey) {
		String expectedTitle = getEnvProperty(expectedTitleKey);
		String actualTitle=driver.getTitle();
		if(expectedTitle.equals(actualTitle))
			return true;
		else
			return false;
	}
	// presence and visibility
	public boolean isElementPresent(String locatorKey) {
		By locator = getLocator(locatorKey);
		
		try {
		  // present and visible
		  WebDriverWait wait = new WebDriverWait(driver,10);
		  wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		 
		}catch(Exception ex) {
			return false;
		}
		return true;
	}
	
	public boolean isElementNotVisible(String locatorKey) {
		By locator = getLocator(locatorKey);
		
		try {
		  
		  WebDriverWait wait = new WebDriverWait(driver,20);
		  wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		 
		}catch(Exception ex) {
			return false;
		}
		return true;
	}
	
	
	
	/*************Utility Functions******************/
	
	public void init(ExtentTest test) {
    	this.test=test;
    }
    

    
    public void log(String msg) {
		System.out.println(msg);
		test.log(Status.INFO, msg);
	}
    
    public void logFailure(String msg, boolean stopExecution) {
    	System.out.println("FAILURE---- "+ msg);
    	takeScreenShot(msg);
    	test.log(Status.FAIL, msg);
    	// fail in testng-cucumber
    //	Assert.fail(msg);// hard assertion
    	softAssert.fail(msg);
    	report.flush();
    	if(stopExecution)
    		softAssert.assertAll();
    }


	public void quit() {
		
		if(driver !=null)

			driver.quit();
		
		if(softAssert!=null)
			softAssert.assertAll();
		
	}
	
	public void switchToFrame(String locatorKey) {
		
		WebElement e=findElement(locatorKey);
		driver.switchTo().frame(e);
		
	}
	
public void switchToDefaultContent() {
		
		driver.switchTo().defaultContent();
		
	}

public void acceptAlert() {
	
	Alert alert=driver.switchTo().alert();
	alert.accept();
	log(alert.getText()+" alert accepted");
}
	
	public void takeScreenShot(String msg) {
		TakesScreenshot takeScreenShot =((TakesScreenshot)driver);
		File SrcFile=takeScreenShot.getScreenshotAs(OutputType.FILE);
		String dateName=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		File DestFile=new File(ExtentReportManager.screenshotFolderPath+"/"+msg+"_"+dateName+".png");
		 try {
			FileUtils.copyFile(SrcFile, DestFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}