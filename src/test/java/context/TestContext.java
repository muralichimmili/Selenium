package context;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;


import managers.PageObjectManager;
import reporters.ExtentReportManager;

public class TestContext {
	ExtentReports report ;
	ExtentTest test;
	private Map<String,String> dictionary;
	private PageObjectManager pageObjectManager;

	
	
	public TestContext() {
		 dictionary = new HashMap<String,String>();
		 report = ExtentReportManager.getReports();
		 this.pageObjectManager = new PageObjectManager();
		 
	}
	
	public PageObjectManager getPageObjectManager() {
		return pageObjectManager;
	}
	
	public void createScenario(String scenarioName) {
		
		test = report.createTest(scenarioName);
		this.pageObjectManager.getWebDriverManager().init(test);
		
	}
	
	public void endScenario() {
		
		this.pageObjectManager.getWebDriverManager().quit();
		report.flush();
		
		
	}
	
	public void log(String msg) {
		this.pageObjectManager.getWebDriverManager().log(msg);
	}
	
	public void setDictionaryvalue(String key,String value) {
		
		dictionary.put(key, value);
	}
	
	public String getDictionaryValue(String key) {
		
		return dictionary.get(key);
	}



	
}
