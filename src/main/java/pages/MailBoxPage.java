package pages;


import java.util.List;

import com.aventstack.extentreports.gherkin.model.Scenario;

import managers.WebDriverManager;
import teststeps.data.MailData;


public class MailBoxPage {
	
WebDriverManager app;
	
	public MailBoxPage(WebDriverManager app) {
		this.app=app;
	}
	
public void logoutFromRediff() {
		
		app.click("logout_class");
		
		
	}

public void verifyWelcomeMsgInMailBox(String expectedVal) {
    String actualVal;
    actualVal=app.findElement("welcome_msg_xp").getText();
    if(!actualVal.equals(expectedVal))
    	app.logFailure(actualVal+" not equal to "+expectedVal, false);
}

public void clickOnWriteMail() {
	app.click("newmail_xp");
}

public void verifyNewMailIsPresent() {
	if(!app.isElementPresent("newmail_lnktxt"))
		app.logFailure("newmail_lnktxt not present", false);
}

public void enterMailDetails(List<MailData> mailData) {
	   
		app.type("enterMail_xp", mailData.get(0).email);
		app.type("subject_css", mailData.get(0).subject);
		app.switchToFrame("iframe_css");
		app.type("body_css", mailData.get(0).bodyText);
		app.switchToDefaultContent();
		
	}

public void clickOnSendMail() {
	app.click("send_lnktxt");
}

public void clickOnSentlink() {
	app.click("sent_xp");
}

public void selectCheckBoxOfSentMail() {
	app.click("tickbox_css");
}

public void clickOnDeleteBtn() {
	app.click("delete_xp");
}

public void clickOnOkBtn() {
	String actualVal;
	actualVal=app.findElement("confirmdel_class").getText();
	if(!actualVal.equals(app.getEnvProperty("delconfirmMsg")))
		app.logFailure(actualVal+" is not equal to "+app.getEnvProperty("delconfirmMsg"), false);
	app.click("delOk_xp");
}

public void notifyMsgNotVisible() {
	
	app.isElementNotVisible("notify_id");
}

public void verifySentMailIsPresent() {
	app.isElementPresent("sentmailfilters_css");
}

public void verifyMailSentDetails(String expectedVal) {
	String actualVal;
	actualVal=app.findElement("sender_css").getAttribute("sender");
	 if(!actualVal.equals(expectedVal))
	    	app.logFailure(actualVal+" not equal to "+expectedVal, false);
}

public void verifyLogOutMsgPresent() {
	app.isElementPresent("signout_xp");
}


}
