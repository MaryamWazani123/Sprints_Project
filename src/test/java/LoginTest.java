import org.example.pom.Login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.*;
import org.testng.Assert;

import java.time.Duration;

public class LoginTest {
    WebDriver driver;
    Login login;

    @BeforeTest
    public void setUp(){
        driver = new ChromeDriver();
        login = new Login(driver);
        driver.manage().window().maximize();
        login.open();

    }

    @Test
    public void test_valid_credentials() {

        login.login_credentials("mariammohd890@gmail.com","Wazani@2000");
        String expectedUrl = "https://magento.softwaretestingboard.com/customer/account/";
        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(currentUrl.contains(expectedUrl),"Login failed with valid credentials. Expected URL: " + expectedUrl + " but found: "
                + currentUrl);


    }

    @Test
    public void test_invalid_email() {

        login.login_credentials("mariam@gmail.com","Wazani@2000");

        WebElement errorMessageElement = driver.findElement(By.cssSelector("div.message-error"));

        String errorMessage = errorMessageElement.getText();

        String expectedErrorMessage = "The account email was incorrect or your account is disabled temporarily. Please wait and try again later.";
        Assert.assertTrue(errorMessage.contains(expectedErrorMessage), "Error message not found! Expected: " + expectedErrorMessage + " but found: " + errorMessage);
    }
    @Test
    public void test_invalid_password() {

        login.login_credentials("mariammohd890@gmail.com","Wazani");

        WebElement errorMessageElement = driver.findElement(By.cssSelector("div.message-error"));

        String errorMessage = errorMessageElement.getText();

        String expectedErrorMessage = "The account password was incorrect or your account is disabled temporarily. Please wait and try again later.";
        Assert.assertTrue(errorMessage.contains(expectedErrorMessage), "Error message not found! Expected: " + expectedErrorMessage + " but found: " + errorMessage);
    }
    @Test
    public void test_empty_email() {

        login.login_credentials("","Wazani@2000");

        WebElement errorMessageElement = driver.findElement(By.id("email-error"));

        String errorMessage = errorMessageElement.getText();

        String expectedErrorMessage = "This is a required field.";
        Assert.assertTrue(errorMessage.contains(expectedErrorMessage), "Error message not found! Expected: " + expectedErrorMessage + " but found: " + errorMessage);

    }
    @Test
    public void test_empty_password() {

        login.login_credentials("mariammohd890@gmail.com","");

        WebElement errorMessageElement = driver.findElement(By.id("email-error"));

        String errorMessage = errorMessageElement.getText();

        String expectedErrorMessage = "This is a required field.";
        Assert.assertTrue(errorMessage.contains(expectedErrorMessage), "Error message not found! Expected: " + expectedErrorMessage + " but found: " + errorMessage);

    }
    @Test
    public void test_logout() {

        login.login_credentials("mariammohd890@gmail.com","Wazani@2000");

        WebElement DropDownMenu = driver.findElement(By.xpath("/html/body/div[2]/header/div[1]/div/ul/li[2]/span/button"));
        DropDownMenu.click();
        WebElement LogoutLink = driver.findElement(By.className("authorization-link"));
        LogoutLink.click();
        String expectedUrl = "https://magento.softwaretestingboard.com/";
        String currentUrl = driver.getCurrentUrl();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        Assert.assertTrue(currentUrl.contains(expectedUrl),"Login failed with valid credentials. Expected URL: " + expectedUrl + " but found: "
                + currentUrl);

    }

//    @AfterTest
//    public void Logout() {
//        WebElement DropDownMenu = driver.findElement(By.xpath("/html/body/div[2]/header/div[1]/div/ul/li[2]/span/button"));
//        DropDownMenu.click();
//        WebElement LogoutLink = driver.findElement(By.className("authorization-link"));
//        LogoutLink.click();
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//    }


}
