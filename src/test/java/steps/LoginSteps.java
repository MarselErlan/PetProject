package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import pages.LoginPage;
import utilities.Config;
import utilities.Driver;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class LoginSteps {



    @Given("user is on the log in page")
    public void user_is_on_the_log_in_page() {
        Driver.getDriver().get(Config.getProperty("Saudemo"));

    }


    @Then("user provides a valid username")
    public void user_provides_a_valid_username() {
        LoginPage loginPage = new LoginPage();
        loginPage.enterUserName.sendKeys(Config.getProperty("username"));
    }
    @Then("user provides a valid password")
    public void user_provides_a_valid_password() {
        LoginPage loginPage = new LoginPage();
        loginPage.enterPassword.sendKeys(Config.getProperty("password"));

    }
    @Then("user click on login button")
    public void user_click_on_login_button() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginButton.click();
    }
    @Then("user logged in")
    public void user_logged_in() {
        Assert.assertEquals(Driver.getDriver().getCurrentUrl(),  "https://www.saucedemo.com/inventory.html");
    }


}
