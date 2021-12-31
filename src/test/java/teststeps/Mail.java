package teststeps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.*;
import teststeps.data.MailData;

import java.util.List;
import java.util.Map;

import context.TestContext;

public class Mail {
	TestContext context;
	HomePage homePage;
	LoginPage loginPage;
	MailBoxPage mailBoxPage;
	public Mail(TestContext context) {
		this.context=context;
		this.homePage=context.getPageObjectManager().getHomePage();
		this.loginPage=context.getPageObjectManager().getLoginPage();
		this.mailBoxPage=context.getPageObjectManager().getMailBoxPage();
	}
	
	@Before
	public void before(Scenario scenario) {		
		context.createScenario(scenario.getName());
		context.log("Starting scenario "+ scenario.getName());
	}
	
	@After
	public void after(Scenario scenario) {
		context.log("Ending scenario "+ scenario.getName());
		context.endScenario();
		context.getPageObjectManager().getWebDriverManager().quit();
		
	}
	
	
	
	@Given("user logged in rediff application")
	public void user_logged_in_rediff_application() {
	  homePage.loadApplication();
	  homePage.gotoLoginPage();
	  loginPage.loginToRediff();
	}

	@And("naviagated to mail box page as {string}")
	public void user_naviagated_to_mail_box_page(String message) {
		
		mailBoxPage.verifyWelcomeMsgInMailBox(message);
	}

	@When("user click on write mail in mail page")
	public void user_click_on_write_mail_in_mail_page() {
		mailBoxPage.clickOnWriteMail();
	}

	@And("navigated to compose mail page")
	public void navigated_to_compose_mail_page() {
		mailBoxPage.verifyNewMailIsPresent();
	}

	
	@And("enter mail details")
	public void enter_mail_details(List<MailData> mailData) {
		context.setDictionaryvalue("email", mailData.get(0).email);
		mailBoxPage.enterMailDetails(mailData);
		
	}
	
	@DataTableType
    public MailData entry(Map<String, String> entry) {
        return new MailData(entry.get("Email"),entry.get("Subject"),entry.get("Text"));
    }

	@And("click on send button")
	public void click_on_send_button() {
	   
		mailBoxPage.clickOnSendMail();
		mailBoxPage.notifyMsgNotVisible();

	}

	@When("click on sent link")
	public void click_on_sent_link() {
	  mailBoxPage.clickOnSentlink();
	}

	@And("navigated to sent mail page")
	public void navigated_to_sent_mail_page() {
		mailBoxPage.verifySentMailIsPresent();
	    
	}

	@Then("verify the mail sent details")
	public void verify_the_mail_sent_details() {
		mailBoxPage.verifyMailSentDetails(context.getDictionaryValue("email"));
	}

	@And("click on logout button")
	public void click_on_logout_button() {
	   mailBoxPage.logoutFromRediff();
	}
	
	@And("select a checkbox of sent mail")
	public void select_a_checkbox_of_sent_mail() {
	   mailBoxPage.selectCheckBoxOfSentMail();
	}
	
	
	@Then("verify user log out from rediff")
	public void verify_user_log_out_from_rediff() {
		mailBoxPage.verifyLogOutMsgPresent();

	}
	
	@And("click on delete button")
	public void click_on_delete_button() {
	   mailBoxPage.clickOnDeleteBtn();
	}
	
	@And("click on ok button")
	public void click_on_ok_button() {
	   mailBoxPage.clickOnOkBtn();
	  
	}
	
}
