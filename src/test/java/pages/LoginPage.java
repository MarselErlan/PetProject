package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class LoginPage {

    WebDriver driver;


    public LoginPage(){
        driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@name=\"user-name\"]")
    public WebElement enterUserName;

    @FindBy(xpath = "//input[@name=\"password\"]")
    public WebElement enterPassword;

    @FindBy(xpath = "//input[@name=\"login-button\"]")
    public WebElement loginButton;


}
