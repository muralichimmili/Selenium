package managers;

import pages.HomePage;
import pages.LoginPage;
import pages.MailBoxPage;

public class PageObjectManager {
	
	HomePage homePage;
	LoginPage loginPage;
    MailBoxPage mailBoxPage;

	WebDriverManager app;
	
	public PageObjectManager() {
		this.app = new WebDriverManager();
	}
	
	
	public WebDriverManager getWebDriverManager() {
		return app;
	}
	
	
	public HomePage getHomePage() {
		if(homePage == null)
			homePage = new HomePage(app);
		
		return homePage;
	}

	public LoginPage getLoginPage() {
		if(loginPage == null)
			loginPage = new LoginPage(app);
		
		return loginPage;
	}
	
	public MailBoxPage getMailBoxPage() {
		if(mailBoxPage == null)
			mailBoxPage = new MailBoxPage(app);
		
		return mailBoxPage;
	}
	
	
	
	
}

