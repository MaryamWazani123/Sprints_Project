package org.example.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
//import sun.jvm.hotspot.utilities.Assert;

public class Login {
    WebDriver driver;

    By email = By.cssSelector("input[type=\"email\"]");
    By password = By.cssSelector("input[type=\"password\"]");
    By Button = By.id("send2");

    public Login(WebDriver driver) {

        this.driver = driver;
    }

    public void open(){
        driver.get("https://magento.softwaretestingboard.com/customer/account/login/referer/aHR0cHM6Ly9tYWdlbnRvLnNvZnR3YXJldGVzdGluZ2JvYXJkLmNvbS8%2C/");
    }

    public void login_credentials(String email, String password){
        driver.findElement(this.email).sendKeys(email);
        driver.findElement(this.password).sendKeys(password);
        driver.findElement(this.Button).click();

    }

}
