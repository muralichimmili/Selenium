package pages;

import managers.WebDriverManager;

public class LoginPage {
	
WebDriverManager app;
	
	public LoginPage(WebDriverManager app) {
		this.app=app;
	}
	
	
	public void loginToRediff() {
		
		app.type("username_name", app.getEnvProperty("username"));
		app.type("password_id", app.getEnvProperty("password"));
		app.click("signIn_css");
		if(!app.isElementPresent("logout_class"))
			app.logFailure("Mail Box Page did not load", true);
		
	}


}
