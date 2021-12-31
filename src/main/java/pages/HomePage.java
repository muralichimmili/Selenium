package pages;

import managers.WebDriverManager;

public class HomePage {
	
	WebDriverManager app;
	
	public HomePage(WebDriverManager app) {
		this.app=app;
	}
	
	
	public void loadApplication() {
		app.log("Trying to load home page after launching browser");
		app.openBrowser(app.getEnvProperty("browser"));
		app.navigateToApplication();
		if(!app.verifyTitle("homePageTitle"))
			app.logFailure("Titles did not match",false);
		
	}
	
	public void gotoLoginPage() {
		app.log("Trying to load login Page");
		app.click("signin_xp");
		if(!app.isElementPresent("username_name"))
			app.logFailure("Login Page did not load", true);
		
	}

}
